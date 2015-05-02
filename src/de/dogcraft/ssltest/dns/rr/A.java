package de.dogcraft.ssltest.dns.rr;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class A extends RR {

    private InetAddress ip4address;

    @Override
    protected boolean parseRData(byte[] rdata, boolean setState) {
        if (rdata.length != 4) {
            return false;
        }

        InetAddress address;
        try {
            address = InetAddress.getByAddress(rdata);
        } catch (UnknownHostException e) {
            return false;
        }

        if (setState) {
            ip4address = address;
        }

        return true;
    }

}
