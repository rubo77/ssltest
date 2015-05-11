package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

public abstract class Address extends Entity {

    protected InetAddress value;

    public abstract int getAddressFamilySize();

    public abstract boolean checkValue(InetAddress ia);

    @Override
    protected void initCoders() {
        getEncoders().put(RRTextEncoder.class, AddressTextTokenEncoder.class);
        getDecoders().put(RRTextDecoder.class, AddressTextTokenDecoder.class);

        getEncoders().put(RRBinaryEncoder.class, AddressBinaryTokenEncoder.class);
        getDecoders().put(RRBinaryDecoder.class, AddressBinaryTokenDecoder.class);
    }

    public class AddressTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            ByteBuffer rbb = readUnquoted(ibb);

            if (rbb == null) {
                return false;
            }

            byte[] addr = rbb.array();

            try {
                value = InetAddress.getByName(new String(addr, StandardCharsets.US_ASCII));
            } catch (UnknownHostException e) {
                return false;
            }

            if ( !checkValue(value)) {
                return false;
            }

            ibb.position(ibb.position() + rbb.capacity());
            skipWhitespace(ibb);

            return true;
        }

    }

    public class AddressTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            os.write(value.getHostAddress().getBytes(StandardCharsets.US_ASCII));
        }

    }

    public class AddressBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            if (ibb.remaining() < getAddressFamilySize()) {
                return false;
            }

            byte[] addr = new byte[getAddressFamilySize()];
            ibb.get(addr);
            try {
                value = InetAddress.getByAddress(addr);
            } catch (UnknownHostException e) {
                return false;
            }

            return checkValue(value);
        }

    }

    public class AddressBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            os.write(value.getAddress());
        }

    }

}
