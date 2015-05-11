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

public class DNSName extends Entity {

    protected String value;

    public boolean checkValue(String value2) {
        return true;
    }

    @Override
    protected void initCoders() {
        getEncoders().put(RRTextEncoder.class, DNSNameTextTokenEncoder.class);
        getDecoders().put(RRTextDecoder.class, DNSNameTextTokenDecoder.class);

        getEncoders().put(RRBinaryEncoder.class, DNSNameBinaryTokenEncoder.class);
        getDecoders().put(RRBinaryDecoder.class, DNSNameBinaryTokenDecoder.class);
    }

    public class DNSNameTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            ByteBuffer rbb = readUnquoted(ibb);

            if (rbb == null) {
                return false;
            }

            byte[] addr = rbb.array();

            value = new String(addr, StandardCharsets.US_ASCII);

            if ( !checkValue(value)) {
                return false;
            }

            ibb.position(ibb.position() + rbb.capacity());
            skipWhitespace(ibb);

            return true;
        }

    }

    public class DNSNameTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            os.write(value.getBytes(StandardCharsets.US_ASCII));
        }

    }

    public class DNSNameBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            return false;
            /*
             * if (ibb.remaining() < getAddressFamilySize()) { return false; }
             * byte[] addr = new byte[getAddressFamilySize()]; ibb.get(addr);
             * try { value = InetAddress.getByAddress(addr); } catch
             * (UnknownHostException e) { return false; } return
             * checkValue(value);
             */
        }

    }

    public class DNSNameBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            /*
             * os.write(value.getAddress());
             */
        }

    }

}
