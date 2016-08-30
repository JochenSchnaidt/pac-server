package com.prodyna.pac.exception;

/**
 * This exception is thrown when the data is not valid for processing.
 */
public class ValidationException extends RuntimeException {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ValidationException
     */
    public ValidationException() {
        super();
    }

    /**
     * Instantiates a new ValidationException
     *
     * @param message
     *            the message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ValidationException
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ValidationException
     *
     * @param cause
     *            the cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

}
