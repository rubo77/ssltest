package de.dogcraft.ssltest.dns.encoding;

public enum Opcode {
    QUERY(0),
    IQUERY(1),
    STATUS(2),

    NOTIFY(4),
    UPDATE(5);

    public final int id;

    private Opcode(int id) {
        this.id = id;
    }

}
