package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.dogcraft.ssltest.dns.encoding.Context;
import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.EntityContainer;
import de.dogcraft.ssltest.dns.encoding.RRBinaryDecoder;
import de.dogcraft.ssltest.dns.encoding.RRBinaryEncoder;
import de.dogcraft.ssltest.dns.encoding.RRDecoder;
import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder;
import de.dogcraft.ssltest.dns.encoding.RREncoder.TokenEncoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextDecoder.TextTokenDecoder;
import de.dogcraft.ssltest.dns.encoding.RRTextEncoder;

public class Container extends Entity {

    private final List<EntityContainer<? extends Entity>> format = new ArrayList<EntityContainer<? extends Entity>>();

    protected List<List<EntityContainer<? extends Entity>>> values = new ArrayList<List<EntityContainer<? extends Entity>>>();

    @SafeVarargs
    public Container(EntityContainer<? extends Entity>... format) {
        this.format.clear();
        this.format.addAll(Arrays.asList(format));
    }

    @Override
    protected void initCoders() {
        getEncoders().put(RRTextEncoder.class, ContainerTextTokenEncoder.class);
        getDecoders().put(RRTextDecoder.class, ContainerTextTokenDecoder.class);

        getEncoders().put(RRBinaryEncoder.class, ContainerBinaryTokenEncoder.class);
        getDecoders().put(RRBinaryDecoder.class, ContainerBinaryTokenDecoder.class);
    }

    public class ContainerTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            if (ibb.remaining() < 1) {
                return false;
            }

            boolean success = true;
            do {
                ByteBuffer rbb = ibb.slice();

                List<EntityContainer<? extends Entity>> immvalues = new ArrayList<EntityContainer<? extends Entity>>(format);

                for (EntityContainer<? extends Entity> imm : immvalues) {
                    Class<? extends RRDecoder> rrd = ctx.getDecoder().getClass();
                    Class<? extends TokenDecoder> td = imm.getEntity().getDecoders().get(rrd);
                    try {
                        td.newInstance().decodeFrom(rbb, ctx);
                    } catch (InstantiationException | IllegalAccessException e) {
                        return false;
                    }
                }

                if (success) {
                    values.add(immvalues);

                    ibb.position(ibb.position() + rbb.position());
                }
            } while (success);

            return !values.isEmpty(); // && checkValue(values);
        }

    }

    public class ContainerTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            for (List<EntityContainer<? extends Entity>> immvalues : values) {
                for (EntityContainer<? extends Entity> imm : immvalues) {
                    Class<? extends RREncoder> rre = ctx.getEncoder().getClass();
                    Class<? extends TokenEncoder> ee = imm.getEntity().getEncoders().get(rre);
                    try {
                        ee.newInstance().encodeTo(os, ctx);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new IOException(e);
                    }
                }
            }
        }

    }

    public class ContainerBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb, Context ctx) throws IOException {
            if (ibb.remaining() < 1) {
                return false;
            }

            boolean success = true;
            do {
                ByteBuffer rbb = ibb.slice();

                List<EntityContainer<? extends Entity>> immvalues = new ArrayList<EntityContainer<? extends Entity>>(format);

                for (EntityContainer<? extends Entity> imm : immvalues) {
                    Class<? extends RRDecoder> rrd = ctx.getDecoder().getClass();
                    Class<? extends TokenDecoder> td = imm.getEntity().getDecoders().get(rrd);
                    try {
                        td.newInstance().decodeFrom(rbb, ctx);
                    } catch (InstantiationException | IllegalAccessException e) {
                        return false;
                    }
                }

                if (success) {
                    values.add(immvalues);

                    ibb.position(ibb.position() + rbb.position());
                }
            } while (success);

            return !values.isEmpty(); // && checkValue(values);
        }

    }

    public class ContainerBinaryTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os, Context ctx) throws IOException {
            for (List<EntityContainer<? extends Entity>> immvalues : values) {
                for (EntityContainer<? extends Entity> imm : immvalues) {
                    Class<? extends RREncoder> rre = ctx.getEncoder().getClass();
                    Class<? extends TokenEncoder> ee = imm.getEntity().getEncoders().get(rre);
                    try {
                        ee.newInstance().encodeTo(os, ctx);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new IOException(e);
                    }
                }
            }
        }

    }

}
