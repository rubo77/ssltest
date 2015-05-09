package de.dogcraft.ssltest.dns.encoding;

import de.dogcraft.ssltest.dns.rr.RR;

public class RRTextEncoder extends RREncoder {

    @Override
    public int encode(RR rr, boolean isQuery) {
        return 0;
    }

}
