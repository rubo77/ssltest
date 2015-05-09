package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder.TokenEncoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder.TextTokenDecoder;

public abstract class Address extends Entity {

    protected InetAddress value;

    public abstract int getAddressFamilySize();

    public abstract boolean checkValue(InetAddress ia);

    public abstract class AddressTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
            ByteBuffer rbb = readUnquoted(ibb);

            if (rbb == null) {
                return false;
            }

            byte[] addr = rbb.array();

            try {
                value = InetAddress.getByName(new String(addr, "US-ASCII"));
            } catch (UnknownHostException | UnsupportedEncodingException e) {
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

    public abstract class AddressTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os) throws IOException {
            os.write(value.getHostAddress().getBytes(Charset.forName("ASCII")));
        }

    }

    public abstract class AddressBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
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

    public abstract class AddressBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os) throws IOException {
            os.write(value.getAddress());
        }

    }

}
