package de.dogcraft.ssltest.dns.encoding;

import java.util.ArrayList;
import java.util.List;

import de.dogcraft.ssltest.dns.rr.RR;

public class Section {

    public final boolean hasRData;

    private final List<RR> records = new ArrayList<RR>();

    public Section(boolean hasRData) {
        this.hasRData = hasRData;
    }

    public List<RR> getRecords() {
        return records;
    }

}
