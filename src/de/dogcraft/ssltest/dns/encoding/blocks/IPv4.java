package de.dogcraft.ssltest.dns.encoding.blocks;

import java.net.Inet4Address;
import java.net.InetAddress;

public class IPv4 extends Address {

    @Override
    public int getAddressFamilySize() {
        return 4;
    }

    @Override
    public boolean checkValue(InetAddress ia) {
        return ia instanceof Inet4Address;
    }

}
