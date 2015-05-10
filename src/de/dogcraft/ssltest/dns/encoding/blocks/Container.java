package de.dogcraft.ssltest.dns.encoding.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.EntityContainer;

public class Container extends Entity {

    private final List<EntityContainer<? extends Entity>> format = new ArrayList<EntityContainer<? extends Entity>>();

    @SafeVarargs
    public Container(EntityContainer<? extends Entity>... format) {
        this.format.clear();
        this.format.addAll(Arrays.asList(format));
    }

}
