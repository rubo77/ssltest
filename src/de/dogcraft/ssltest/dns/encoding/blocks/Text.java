package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import de.dogcraft.ssltest.dns.encoding.Context;
import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.RRBinaryDecoder;
import de.dogcraft.ssltest.dns.encoding.RRBinaryEncoder;
import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder.TokenEncoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder.TextTokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextEncoder;

public class Text extends Entity {

    protected String value;

    @Override
    protected void initCoders() {
        getEncoders().put(RRTextEncoder.class, CharStringTextTokenEncoder.class);
        getDecoders().put(RRTextDecoder.class, CharStringTextTokenDecoder.class);

        getEncoders().put(RRBinaryEncoder.class, CharStringBinaryTokenEncoder.class);
        getDecoders().put(RRBinaryDecoder.class, CharStringBinaryTokenDecoder.class);
    }

    public class CharStringTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            ByteBuffer rbb = ibb.slice();

            if (rbb.remaining() < 2) {
                return false;
            }

            byte b = rbb.get();

            if ('"' != b) {
                return false;
            }

            while (rbb.remaining() > 0) {
                b = rbb.get();
                if ('\\' == b) {
                    if (rbb.remaining() < 1) {
                        return false;
                    }
                    rbb.get();
                } else if ('"' == b) {
                    break;
                }
            }

            rbb.limit();
            rbb.position(0);

            byte[] addr = rbb.array();

            value = new String(addr, StandardCharsets.US_ASCII);
            for (int pos = 0; pos < value.length() - 1; pos++) {
                if (value.charAt(pos) == '\\') {
                    value = value.substring(0, pos) + value.substring(pos + 1);
                }
            }

            ibb.position(ibb.position() + rbb.limit());
            skipWhitespace(ibb);

            return true;
        }

    }

    public class CharStringTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            os.write('"');
            os.write(value.getBytes(StandardCharsets.US_ASCII));
            os.write('"');
        }

    }

    public class CharStringBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            if (ibb.remaining() < 1) {
                return false;
            }

            ByteBuffer rbb = ibb.slice();

            byte b = rbb.get();

            if (rbb.remaining() < b) {
                return false;
            }

            byte[] bytes = new byte[b];
            rbb.get(bytes);

            value = new String(bytes, StandardCharsets.US_ASCII);

            ibb.position(ibb.position() + rbb.position());
            return true;
        }

    }

    public class CharStringBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            os.write(value.getBytes(StandardCharsets.US_ASCII).length);
            os.write(value.getBytes(StandardCharsets.US_ASCII));
        }

    }

}
