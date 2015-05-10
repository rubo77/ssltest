package de.dogcraft.ssltest.dns.encoding;

import java.util.HashMap;

import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder.TokenEncoder;

public abstract class Entity {

    protected final HashMap<Class<? extends RREncoder>, Class<? extends RREncoder.TokenEncoder>> encoders = new HashMap<Class<? extends RREncoder>, Class<? extends TokenEncoder>>();

    protected final HashMap<Class<? extends RRDecoder>, Class<? extends RRDecoder.TokenDecoder>> decoders = new HashMap<Class<? extends RRDecoder>, Class<? extends TokenDecoder>>();

    protected abstract void initCoders();

    public Entity() {
        initCoders();
    }

}
