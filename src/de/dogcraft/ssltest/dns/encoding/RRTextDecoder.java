package de.dogcraft.ssltest.dns.encoding;

import java.nio.ByteBuffer;

import de.dogcraft.ssltest.dns.rr.RR;

public class RRTextDecoder extends RRDecoder {

    @Override
    public RR decode(ByteBuffer ibb, boolean isQuery) {
        return null;
    }

    public abstract static class TextTokenDecoder implements TokenDecoder {

        public void skipWhitespace(ByteBuffer ibb) {
            while (ibb.remaining() > 0 && Character.isWhitespace(ibb.get())) {
                // Nothing to do here ...
            }
        }

        public ByteBuffer readUnquoted(ByteBuffer ibb) {
            ByteBuffer rbb = ibb.slice();

            while (rbb.remaining() > 0) {
                byte b = rbb.get();
                if (0 == b || 9 == b || 32 == b || Character.isWhitespace(b)) {
                    break;
                }
            }

            rbb.limit();
            rbb.position(0);

            return rbb;
        }

    }

}
