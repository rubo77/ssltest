package de.dogcraft.ssltest.dns.encoding;

import java.io.IOException;
import java.io.OutputStream;

import de.dogcraft.ssltest.dns.rr.RR;

public abstract class RREncoder {

    public int encode(RR rr) throws IOException {
        return encode(rr, false);
    }

    public abstract int encode(RR rr, boolean isQuery) throws IOException;

    public interface TokenEncoder {

        public void encodeTo(OutputStream os) throws IOException;

    }

}
