package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.RRBinaryDecoder;
import de.dogcraft.ssltest.dns.encoding.RRBinaryEncoder;
import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder.TokenEncoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder.TextTokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextEncoder;

public abstract class Integer extends Entity {

    protected BigInteger value;

    public abstract int getSize();

    public boolean checkValue(BigInteger bi) {
        return bi.bitLength() < 8 * getSize();
    }

    @Override
    protected void initCoders() {
        encoders.put(RRTextEncoder.class, IntegerTextTokenEncoder.class);
        decoders.put(RRTextDecoder.class, IntegerTextTokenDecoder.class);

        encoders.put(RRBinaryEncoder.class, IntegerBinaryTokenEncoder.class);
        decoders.put(RRBinaryDecoder.class, IntegerBinaryTokenDecoder.class);
    }

    public class IntegerTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
            ByteBuffer rbb = readUnquoted(ibb);

            if (rbb == null) {
                return false;
            }

            byte[] bytes = rbb.array();

            value = new BigInteger(new String(bytes, StandardCharsets.US_ASCII));

            if ( !checkValue(value)) {
                return false;
            }

            ibb.position(ibb.position() + rbb.capacity());
            skipWhitespace(ibb);

            return true;
        }

    }

    public class IntegerTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os) throws IOException {
            os.write(value.toString(10).getBytes(StandardCharsets.US_ASCII));
        }

    }

    public class IntegerBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
            if (ibb.remaining() < getSize()) {
                return false;
            }

            byte[] bytes = new byte[getSize()];
            ibb.get(bytes);

            value = new BigInteger(bytes);

            return checkValue(value);
        }

    }

    public class IntegerBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os) throws IOException {
            byte[] ba = value.toByteArray();

            for (int a = 0; a < getSize() - ba.length; a++) {
                os.write(0);
            }

            os.write(ba, Math.max(0, getSize() - ba.length), Math.min(getSize(), ba.length));
        }

    }

}
