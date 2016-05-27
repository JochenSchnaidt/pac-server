package com.prodyna.pac.exception;

/**
 * This exception is thrown when an error occured while processing.
 */
public class ProcessingException extends RuntimeException {

    /**
     * Generated default serial version UID
     */
    private static final long serialVersionUID = 3896695426389123465L;

    /**
     * Instantiates a new ProcessingException
     */
    public ProcessingException() {
        super();
    }

    /**
     * Instantiates a new ProcessingException
     *
     * @param message
     *            the message
     */
    public ProcessingException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ProcessingException
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ProcessingException
     *
     * @param cause
     *            the cause
     */
    public ProcessingException(Throwable cause) {
        super(cause);
    }

}
