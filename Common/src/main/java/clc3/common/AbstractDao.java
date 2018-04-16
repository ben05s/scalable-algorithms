package clc3.common;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import java.util.Collection;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractDao<T> {

    protected abstract Class<T> getEntityType();

    public Key<T> getKey(long id) { return Key.create(getEntityType(), id); }

    public Ref<T> getRef(long id) { return Ref.create(getKey(id)); }

    public T getById(long id) {
        return ofy().load().type(getEntityType()).id(id).now();
    }

    public Collection<T> getByIds(Iterable<Long> ids) {
        return ofy().load().type(getEntityType()).ids(ids).values();
    }

    public Collection<T> getByIds(Long... ids) {
        return ofy().load().type(getEntityType()).ids(ids).values();
    }

    public Collection<T> getAll() {
        return ofy().load().type(getEntityType()).list();
    }

    public void deleteById(Long id) {
        ofy().delete().type(getEntityType()).id(id).now();
    }

    public void deleteByIds(Iterable<Long> ids) {
        ofy().delete().type(getEntityType()).ids(ids).now();
    }

    // mapper requires long instead of Long
    public void deleteByIds(long... ids) {
        ofy().delete().type(getEntityType()).ids(ids).now();
    }

    public T getByKey(Key<T> key) {
        return ofy().load().key(key).now();
    }

    public Key<T> save(T entity) {
        return ofy().save().entity(entity).now();
    }

    public Map<Key<T>, T> save(Iterable<T> entities) {
        return ofy().save().entities(entities).now();
    }

    public Map<Key<T>, T> save(T... entities) {
        return ofy().save().entities(entities).now();
    }

    public void delete(T entity) {
        ofy().delete().entity(entity).now();
    }

    public void delete(Iterable<T> entities) {
        ofy().delete().entities(entities).now();
    }

    public void delete(T... entities) {
        ofy().delete().entities(entities).now();
    }

    public void deleteByKey(Key<T> entity) {
        ofy().delete().key(entity).now();
    }

    public void deleteByKeys(Iterable<? extends Key<?>> keys) {
        ofy().delete().keys(keys).now();
    }

    public void deleteByKeys(Key<T>... keys) {
        ofy().delete().keys(keys).now();
    }

    public void clearCache() {
        ofy().clear();
    }
}
