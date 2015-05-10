package de.dogcraft.ssltest.dns.encoding;

public class NamedEntityContainer<E extends Entity> extends EntityContainer<E> {

    public NamedEntityContainer() {
        super();
        this.name = "";
    }

    public NamedEntityContainer(E e) {
        super(e);
        this.name = "";
    }

    public NamedEntityContainer(String name) {
        super();
        this.name = name;
    }

    public NamedEntityContainer(String name, E e) {
        super(e);
        this.name = name;
    }

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
