package net.b0at.api.event.cache;


import com.esotericsoftware.reflectasm.MethodAccess;
import net.b0at.api.event.types.EventPriority;
import net.b0at.api.event.types.EventType;

import java.lang.reflect.Method;
import java.util.NavigableSet;
import java.util.Objects;

/**
 * An encapsulator for a method denoted by {@code EventHandler}, holding the necessary information to invoke the {@link #listener}'s {@link #method}.
 *
 * @param <T> the event base class of this {@link HandlerEncapsulator}
 */
public class HandlerEncapsulator<T> {
    /**
     * The object representing the instance of the listener.
     */
    protected Object listener;

    /**
     * The method in the {@link #listener} that will be invoked.
     */
    protected Method method;

    /**
     * The {@link EventPriority} of this {@link HandlerEncapsulator}, relative to other encapsulators in the {@link #parentSet}.
     */
    private EventPriority priority;

    /**
     * The set of all encapsulators this {@link HandlerEncapsulator} belongs to.
     */
    protected NavigableSet<HandlerEncapsulator<T>> parentSet;

    /**
     * The ASM {@link MethodAccess} used to invoke the {@link #listener}'s {@link #method}.
     */
    protected MethodAccess methodAccess;

    /**
     * The cached ASM method index for the {@link #listener}'s {@link #method}.
     */
    protected int methodIndex;

    /**
     * Constructs a {@link HandlerEncapsulator}, holding the necessary information to invoke the {@link #listener}'s {@link #method}.
     *
     * @param listener the object representing the instance of the {@code listener}
     * @param method the method in the {@link #listener} that will be invoked
     * @param methodIndex the ASM method index for the {@code listener}'s {@code method}
     * @param priority the priority of this {@link HandlerEncapsulator}, relative to other encapsulators in the {@link #parentSet}
     * @param parentSet the set of all encapsulators that contains this {@link HandlerEncapsulator}
     */
    public HandlerEncapsulator(Object listener, Method method, int methodIndex, EventPriority priority, NavigableSet<HandlerEncapsulator<T>> parentSet) {
        this.listener = listener;
        this.method = method;
        this.priority = priority;
        this.parentSet = parentSet;
        this.methodIndex = methodIndex;
        method.setAccessible(true);

        this.methodAccess = MethodAccess.get(this.listener.getClass());
    }

    /**
     * Invokes the {@link #method} in the {@link #listener} with {@code event}, and optional {@code timing}.
     *
     * @see HandlerEncapsulatorWithTiming#invoke for the implementation that uses {@code timing}.
     *
     * @param event the event object to pass to the {@link #listener}'s {@link #method}
     * @param timing the {@link EventType} to pass to the {@link #listener}'s {@link #method} (if applicable)
     */
    public void invoke(T event, EventType timing) {
        this.methodAccess.invoke(this.listener, this.methodIndex, event);
    }

    /**
     * The priority of this {@link HandlerEncapsulator}, relative to other encapsulators in the {@link #parentSet}.
     */
    public final EventPriority getPriority() {
        return this.priority;
    }

    /** Registers or deregisters this {@link HandlerEncapsulator} in the {@link #parentSet}
     *
     * <p>
     * To register a {@link HandlerEncapsulator}, the {@code HandlerEncapsulator} is {@code add}ed to {@link #parentSet}:
     * {@link NavigableSet#add(Object)}
     * <br>
     * To deregister a {{@link HandlerEncapsulator}, the {@code HandlerEncapsulator} is {@code remove}d from {@link #parentSet}:
     * {@link NavigableSet#remove(Object)}
     * </p>
     *
     * @param enabled whether to register ({@code TRUE}), or deregister ({@code FALSE}) this {@link HandlerEncapsulator}
     */
    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.parentSet.add(this);
        } else {
            this.parentSet.remove(this);
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
        if (obj.getClass() == HandlerEncapsulator.class) {
            HandlerEncapsulator other = (HandlerEncapsulator) obj;

            return Objects.equals(this.method, other.method) && Objects.equals(this.listener, other.listener);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s@%s#%s@%s(%s)",
                this.listener.getClass().getName(),
                Integer.toHexString(this.listener.hashCode()),
                this.method.getName(),
                Integer.toHexString(this.method.hashCode()),
                this.method.getParameters()[0].getType().getName());
    }
}
