package de.dogcraft.ssltest.dns.rr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.bouncycastle.util.encoders.Hex;

import de.dogcraft.ssltest.dns.RRClass;
import de.dogcraft.ssltest.dns.RRType;

public class RR {

    private String label;

    private long ttl;

    private RRClass rrclass;

    private int type;

    protected byte[] rdata;

    public RR() {
        label = "";
        ttl = 0;
        rrclass = RRClass.ANY;
        type = RRType.UNKNOWN.id;
        rdata = new byte[0];
    };

    public void fromStream(InputStream is) throws IOException {}

    public void toStream(OutputStream os) throws IOException {}

    public boolean fromTextual(String textual) {
        if ( !textual.startsWith("\\# ")) {
            return false;
        }

        textual = textual.trim();
        int pos = textual.indexOf(" ");
        if ( -1 == pos) {
            if (0 == Integer.parseInt(textual.trim())) {
                return this.setRData(new byte[0]);
            } else {
                return false;
            }
        }

        List<String> dataArr = Arrays.asList(textual.split(" "));
        int len = Integer.parseInt(dataArr.get(0));

        dataArr.remove(0);

        textual = String.join("", dataArr);
        textual = textual.replaceAll("\\((.*?)\\)", "\\1");
        byte[] rdata_bin = Hex.decode(textual);
        if (rdata_bin.length != len) {
            return false;
        }

        return this.setRData(rdata_bin);
    }

    public String toTextual() {
        return toTextual("");
    }

    public String toTextual(String origin) {
        return String.format(Locale.ENGLISH, "\\# %d %s", this.rdata.length, Hex.encode(this.rdata)).trim();
    }

    public byte[] encodeRData() {
        return this.rdata;
    }

    public boolean decodeRData() {
        return parseRData(this.rdata, true);
    }

    public String getLabel() {
        return label;
    }

    protected void setLabel(String label) {
        this.label = label;
    }

    public long getTTL() {
        return ttl;
    }

    public void setTTL(long ttl) {
        this.ttl = ttl;
    }

    public RRClass getRRClass() {
        return rrclass;
    }

    public void setRRClass(RRClass rrclass) {
        this.rrclass = rrclass;
    }

    public int getType() {
        return type;
    };

    protected void setType(int type) {
        this.type = type;
    }

    protected void setType(RRType type) {
        this.type = type.id;
    }

    public byte[] getRData() {
        return rdata;
    }

    public boolean setRData(byte[] rdata) {
        if ( !parseRData(rdata, true)) {
            return false;
        }

        this.rdata = rdata;
        return true;
    }

    protected boolean parseRData(byte[] rdata, boolean setState) {
        return true;
    }

    public boolean isWellKnown() {
        return false;
    }

}
