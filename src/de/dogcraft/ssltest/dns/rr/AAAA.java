package de.dogcraft.ssltest.dns.rr;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AAAA extends RR {

    private InetAddress ip6address;

    @Override
    protected boolean parseRData(byte[] rdata, boolean setState) {
        if (rdata.length != 16) {
            return false;
        }

        InetAddress address;
        try {
            address = InetAddress.getByAddress(rdata);
        } catch (UnknownHostException e) {
            return false;
        }

        if (setState) {
            setIP6Address(address);
        }

        return true;
    }

    @Override
    public boolean isWellKnown() {
        return true;
    }

    @Override
    public String getTypeName() {
        return "AAAA";
    }

    public InetAddress getIP6Address() {
        return ip6address;
    }

    public boolean setIP6Address(InetAddress ip6address) {
        if ( !(ip6address instanceof Inet6Address)) {
            return false;
        }

        this.ip6address = ip6address;

        this.rdata = this.ip6address.getAddress();
        return true;
    }

}
