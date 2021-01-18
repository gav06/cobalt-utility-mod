package net.b0at.api.event.exceptions;

/**
 * {@code ListenerAlreadyRegisteredException}'s are thrown when an already registered listener is attempted to be registered again.
 */
public class ListenerAlreadyRegisteredException extends RuntimeException {
    /**
     * The error message to be formatted with the listener class name.
     */
    private static final String ERROR_MESSAGE = "Unable to register listener %s since it is already registered!";

    /**
     * Constructs a new {@code ListenerAlreadyRegisteredException}.
     *
     * @param listener the instance of the {@code listener} that was already registered
     */
    public ListenerAlreadyRegisteredException(Object listener) {
        super(String.format(ERROR_MESSAGE, listener.getClass().getName()));
    }
}
