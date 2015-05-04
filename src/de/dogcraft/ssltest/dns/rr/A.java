package de.dogcraft.ssltest.dns.rr;

import java.net.Inet4Address;
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
            setIP4Address(address);
        }

        return true;
    }

    @Override
    public boolean isWellKnown() {
        return true;
    }

    @Override
    public String getTypeName() {
        return "A";
    }

    @Override
    public boolean fromTextualRData(String textual, String origin) {
        try {
            this.setIP4Address(Inet4Address.getByName(textual));
        } catch (UnknownHostException e) {
            return false;
        }

        return true;
    }

    @Override
    public String toTextualRData() {
        return ip4address.toString();
    }

    public InetAddress getIP4Address() {
        return ip4address;
    }

    public boolean setIP4Address(InetAddress ip4address) {
        if ( !(ip4address instanceof Inet4Address)) {
            return false;
        }

        this.ip4address = ip4address;

        this.rdata = this.ip4address.getAddress();
        return true;
    }

}
