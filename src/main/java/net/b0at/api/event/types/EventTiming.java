package net.b0at.api.event.types;

/**
 * Represents the timing of an {@link EventHandler}.
 *
 * <p>
 * {@link EventHandler}s can be either invoked BEFORE ({@link #PRE}), or AFTER ({@link #POST}) an event occurs.
 * </p>
 */
public enum EventTiming {
    /**
     * {@link EventHandler}s with this {@link EventTiming} are invoked before the event occurs.
     *
     * <p>
     * This is the default {@link EventTiming} for {@link EventHandler}s.
     * </p>
     */
    PRE,

    /**
     * {@link EventHandler}s with this {@link EventTiming} are invoked after the event occurs.
     *
     * <p>
     * Typically, {@link EventHandler}s can not cancel an event with this timing, as cancellation requests
     * are either ignored, or in many cases, illegal.
     * </p>
     */
    POST
}
