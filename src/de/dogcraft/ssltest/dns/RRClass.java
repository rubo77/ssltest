package de.dogcraft.ssltest.dns;

public enum RRClass {
    IN(1),
    CS(2),
    CH(3),
    HS(4),

    NONE(254, true),
    ANY(255, true);

    public final int id;

    public final boolean qclass;

    private RRClass(int id) {
        this(id, false);
    }

    private RRClass(int id, boolean qclass) {
        this.id = id;
        this.qclass = true;
    }

}
