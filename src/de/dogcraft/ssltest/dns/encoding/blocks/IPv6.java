package de.dogcraft.ssltest.dns.encoding.blocks;

import java.net.Inet6Address;
import java.net.InetAddress;

public class IPv6 extends Address {

    @Override
    public int getAddressFamilySize() {
        return 16;
    }

    @Override
    public boolean checkValue(InetAddress ia) {
        return ia instanceof Inet6Address;
    }

}
