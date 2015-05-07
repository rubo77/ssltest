package de.dogcraft.ssltest.dns.encoding;

public enum ResponseCode {
    SUCCESS(0),
    FORMAT_ERROR(1),
    SERVER_FAILURE(2),
    NAME_ERROR(3),
    NOT_IMPLEMENTED(4),
    REFUSED(5),
    YXDOMAIN(6),
    YXRRSET(7),
    NXRRSET(8),
    NOTAUTH(9),
    NOTZONE(10),
    BADVERS(16),
    BADSIG(16),
    BADKEY(17),
    BADTIME(18),
    BADMODE(19),
    BADNAME(20),
    BADALG(21),
    BADTRUNC(22);

    public final int id;

    private ResponseCode(int id) {
        this.id = id;
    }

}
