
package com.spring.example.exception;

/**
 *
 * @author Allen Cao
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Object id;

    public ResourceNotFoundException(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
