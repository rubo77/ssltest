package de.dogcraft.ssltest.dns.encoding.blocks;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.dogcraft.ssltest.dns.encoding.Entity;
import de.dogcraft.ssltest.dns.encoding.EntityContainer;
import de.dogcraft.ssltest.dns.encoding.RRBinaryDecoder;
import de.dogcraft.ssltest.dns.encoding.RRBinaryEncoder;
import de.dogcraft.ssltest.dns.encoding.RRDecoder.TokenDecoder;
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
        encoders.put(RRTextEncoder.class, ContainerTextTokenEncoder.class);
        decoders.put(RRTextDecoder.class, ContainerTextTokenDecoder.class);

        encoders.put(RRBinaryEncoder.class, ContainerBinaryTokenEncoder.class);
        decoders.put(RRBinaryDecoder.class, ContainerBinaryTokenDecoder.class);
    }

    public class ContainerTextTokenDecoder extends TextTokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
            return false;
            /*
             * ByteBuffer rbb = readUnquoted(ibb); if (rbb == null) { return
             * false; } byte[] addr = rbb.array(); try { value =
             * InetAddress.getByName(new String(addr,
             * StandardCharsets.US_ASCII)); } catch (UnknownHostException e) {
             * return false; } if ( !checkValue(value)) { return false; }
             * ibb.position(ibb.position() + rbb.capacity());
             * skipWhitespace(ibb); return true;
             */
        }

    }

    public class ContainerTextTokenEncoder implements TokenEncoder {

        @Override
        public void encodeTo(OutputStream os) throws IOException {
            /*
             * os.write(value.getHostAddress().getBytes(StandardCharsets.US_ASCII
             * ));
             */
        }

    }

    public class ContainerBinaryTokenDecoder implements TokenDecoder {

        @Override
        public boolean decodeFrom(ByteBuffer ibb) {
            if (ibb.remaining() < 1) {
                return false;
            }

            boolean success = true;
            do {
                ByteBuffer rbb = ibb.slice();

                List<EntityContainer<? extends Entity>> immvalues = new ArrayList<EntityContainer<? extends Entity>>(format);

                for (EntityContainer<? extends Entity> imm : immvalues) {
                    success = false;
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
        public void encodeTo(OutputStream os) throws IOException {
            for (List<EntityContainer<? extends Entity>> immvalues : values) {
                for (EntityContainer<? extends Entity> imm : immvalues) {
                    // imm.encodeTo(os);
                    throw new IOException("No encoder found for " + imm.getClass().getName());
                }
            }
        }

    }

}
