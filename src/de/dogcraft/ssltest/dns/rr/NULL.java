package de.dogcraft.ssltest.dns.rr;

public class NULL extends RR {

    @Override
    public boolean isWellKnown() {
        return true;
    }

    @Override
    public String getTypeName() {
        return "NULL";
    }

}
