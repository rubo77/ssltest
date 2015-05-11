package de.dogcraft.ssltest.dns.encoding;

import java.util.HashMap;

import de.dogcraft.ssltest.dns.rr.RR;

public class Context {

    private String origin;

    private Integer ttl;

    private RR previous;

    private HashMap<Class<? extends Entity>, Object> zoneCtx;

    private Object recordCtx;

    private RRDecoder decoder;

    private RREncoder encoder;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public RR getPrevious() {
        return previous;
    }

    public void setPrevious(RR previous) {
        this.previous = previous;
    }

    public Object getZoneCtx(Class<? extends Entity> k) {
        return zoneCtx.get(k);
    }

    public void setZoneCtx(Class<? extends Entity> k, Object o) {
        this.zoneCtx.put(k, o);
    }

    public Object getRecordCtx() {
        return recordCtx;
    }

    public void setRecordCtx(Object recordCtx) {
        this.recordCtx = recordCtx;
    }

    public RRDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(RRDecoder decoder) {
        this.decoder = decoder;
    }

    public RREncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(RREncoder encoder) {
        this.encoder = encoder;
    }

}
