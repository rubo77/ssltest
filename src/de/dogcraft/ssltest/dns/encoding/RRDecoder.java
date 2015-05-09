package de.dogcraft.ssltest.dns.encoding;

import java.io.IOException;
import java.nio.ByteBuffer;

import de.dogcraft.ssltest.dns.rr.RR;

public abstract class RRDecoder {

    public RR decode(ByteBuffer ibb) {
        return decode(ibb, false);
    }

    public abstract RR decode(ByteBuffer ibb, boolean isQuery);

    public static interface TokenDecoder {

        public boolean decodeFrom(ByteBuffer ibb) throws IOException;

    }

}
