package net.b0at.api.event.exceptions;

/**
 * {@code ListenerNotAlreadyRegisteredException}'s are thrown when an unknown or already deregistered listener is attempted to be deregistered.
 */
public class ListenerNotAlreadyRegisteredException extends RuntimeException {
    /**
     * The error message to be formatted with the listener class name.
     */
    private static final String ERROR_MESSAGE = "Unable to deregister listener %s since it is not already registered!";

    /**
     * Constructs a new {@code ListenerNotAlreadyRegisteredException}.
     *
     * @param listener the instance of the {@code listener} that was not already registered
     */
    public ListenerNotAlreadyRegisteredException(Object listener) {
        super(String.format(ERROR_MESSAGE, listener.getClass().getName()));
    }
}
