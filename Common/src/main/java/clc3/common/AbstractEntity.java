package clc3.common;

import com.googlecode.objectify.annotation.Id;

public abstract class AbstractEntity<T> {
    @Id
    Long id;

    public long getId() {
        return id;
    }
}
