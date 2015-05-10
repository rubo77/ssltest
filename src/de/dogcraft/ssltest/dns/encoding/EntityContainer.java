package de.dogcraft.ssltest.dns.encoding;

import java.lang.reflect.ParameterizedType;

public class EntityContainer<E extends Entity> {

    protected abstract static class EntityFactory<T> {

        public final T instance;

        @SuppressWarnings("unchecked")
        public EntityFactory() {
            T i = null;
            try {
                i = ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
            }
            instance = i;
        }

        public T get() {
            return instance;
        }
    }

    private E entity;

    public EntityContainer() {
        this.entity = new EntityFactory<E>() {}.get();
    }

    public EntityContainer(E e) {
        this.entity = e;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

}
