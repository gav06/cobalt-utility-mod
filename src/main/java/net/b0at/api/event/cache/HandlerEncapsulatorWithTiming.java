package net.b0at.api.event.cache;

import net.b0at.api.event.types.EventPriority;
import net.b0at.api.event.types.EventType;

import java.lang.reflect.Method;
import java.util.NavigableSet;
import java.util.Objects;

/**
 * An encapsulator for a method denoted by {@code EventHandler}, holding the necessary information to invoke the {@link #listener}'s {@link #method}.
 *
 * <p>
 * This encapsulator also invokes the {@link Method} with an applicable {@link EventType} argument.
 * </p>
 *
 * @see HandlerEncapsulator
 *
 * @param <T> the event base class of this {@link HandlerEncapsulatorWithTiming}
 */
public class HandlerEncapsulatorWithTiming<T> extends HandlerEncapsulator<T> {
    /**
     * The set of all encapsulators this {@link HandlerEncapsulator} belongs to with timing {@link EventType#POST}.
     */
    private NavigableSet<HandlerEncapsulator<T>> postParentSet;

    /**
     * Constructs a {@link HandlerEncapsulatorWithTiming}, holding the necessary information to invoke the {@link #listener}'s {@link #method}.
     *
     * @param listener the object representing the instance of the {@code listener}
     * @param method the method in the {@link #listener} that will be invoked
     * @param methodIndex the ASM method index for the {@code listener}'s {@code method}
     * @param priority the priority of this encapsulator, relative to other encapsulators in the {@link #parentSet}
     * @param preParentSet the set of all encapsulators with timing {@link EventType#PRE} that contains this {@link HandlerEncapsulatorWithTiming}
     * @param postParentSet the set of all encapsulators with timing {@link EventType#POST} that contains this {@link HandlerEncapsulatorWithTiming}
     */
    public HandlerEncapsulatorWithTiming(Object listener, Method method, int methodIndex, EventPriority priority, NavigableSet<HandlerEncapsulator<T>> preParentSet, NavigableSet<HandlerEncapsulator<T>> postParentSet) {
        super(listener, method, methodIndex, priority, preParentSet);

        this.postParentSet = postParentSet;
    }

    /**
     * Invokes the {@link #method} in the {@link #listener} with {@code event} {@code timing}.
     *
     * @see HandlerEncapsulator#invoke for the implementation that does not use {@code timing}.
     *
     * @param event the event object to pass to the {@link #listener}'s {@link #method}
     * @param timing the {@link EventType} to pass to the {@link #listener}'s {@link #method}
     */
    @Override
    public void invoke(T event, EventType timing) {
        this.methodAccess.invoke(this.listener, this.methodIndex, event, timing);
    }

    /** Registers or deregisters this {@link HandlerEncapsulatorWithTiming} in {@link #parentSet}  and {@link #postParentSet}
     *
     * <p>
     * To register a {@link HandlerEncapsulatorWithTiming}, the {@code HandlerEncapsulatorWithTiming} is {@code add}ed to {@link #parentSet}  and {@link #postParentSet}:
     * {@link NavigableSet#add(Object)}.
     * <br>
     * To deregister a {@link HandlerEncapsulatorWithTiming}, the {@code HandlerEncapsulatorWithTiming} is {@code remove}d from {@link #parentSet}  and {@link #postParentSet}:
     * {@link NavigableSet#remove(Object)}.
     * </p>
     *
     * @param enabled whether to register ({@code TRUE}), or deregister ({@code FALSE}) this {@link HandlerEncapsulatorWithTiming}
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.parentSet.add(this);
            this.postParentSet.add(this);
        } else {
            this.parentSet.remove(this);
            this.postParentSet.remove(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == HandlerEncapsulatorWithTiming.class) {
            HandlerEncapsulatorWithTiming other = (HandlerEncapsulatorWithTiming) obj;

            return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format("%s@%s#%s@%s(%s, EventPriority priority)",
                this.listener.getClass().getName(),
                Integer.toHexString(this.listener.hashCode()),
                this.method.getName(),
                Integer.toHexString(this.method.hashCode()),
                this.method.getParameters()[0].getType().getName());
    }
}
