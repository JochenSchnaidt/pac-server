package com.prodyna.pac.exception;

/**
 * This exception is thrown when an expected entity was not found for processing.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new EntityNotFoundException
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Instantiates a new EntityNotFoundException
     *
     * @param message
     *            the message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new EntityNotFoundException
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new EntityNotFoundException
     *
     * @param cause
     *            the cause
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
