package net.b0at.api.event.profiler;

import net.b0at.api.event.Subscribe;
import net.b0at.api.event.EventManager;
import net.b0at.api.event.cache.HandlerEncapsulator;
import net.b0at.api.event.types.EventType;

import java.util.NavigableSet;

/**
 * A simple profiler for an {@link EventManager}.
 *
 * <p>
 * This can be used to measure various actions such as registration/deregistration, discovery, and event firing.
 * </p>
 *
 * @see EventManager#setEventProfiler(IEventProfiler)
 *
 * @param <T> the event base class of this {@link IEventProfiler}
 */
public interface IEventProfiler<T> {
    /**
     * Invoked when a {@code listener} is registered.
     *
     * @param listener the instance of the {@code listener} that has been registered
     * @param onlyAddPersistent if this is TRUE, only {@link Subscribe#persistent()} were registered,
     *                          if this is FALSE, only non {@link Subscribe#persistent()} were registered
     */
    default void onRegisterListener(Object listener, boolean onlyAddPersistent) { }

    /**
     * Invoked when a {@code listener} is deregistered.
     *
     * @param listener the instance of the {@code listener} that has been deregistered
     * @param onlyRemovePersistent  if this is TRUE, only {@link Subscribe#persistent()} were deregistered,
     *                              if this is FALSE, only non {@link Subscribe#persistent()} were deregistered
     */
    default void onDeregisterListener(Object listener, boolean onlyRemovePersistent) { }

    /**
     * Invoked before a {@code listener} is searched for {@link Subscribe}'s for the first time.
     *
     * @param listener the instance of the {@code listener} that will be searched
     */
    default void preListenerDiscovery(Object listener) { }

    /**
     * Invoked after a {@code listener} is searched for {@link Subscribe}'s for the first time.
     *
     * @param listener the instance of the {@code listener} that has been searched
     */
    default void postListenerDiscovery(Object listener) { }

    /**
     * Invoked after {@link EventManager#deregisterAll()} is invoked.
     */
    default void onDeregisterAll() { }

    /**
     * Invoked after {@link EventManager#cleanup()} is invoked.
     */
    default void onCleanup() { }

    /**
     * Invoked before an {@code event} is propagated to {@code handlers}.
     *
     * @param event the event to propagate to {@code handlers}
     * @param timing the {@link EventType} of the {@code event}
     * @param handlers the {@link NavigableSet}&lt;{@link HandlerEncapsulator}&gt; that will be invoked
     */
    default void preFireEvent(T event, EventType timing, NavigableSet<HandlerEncapsulator<T>> handlers) { }

    /**
     * Invoked after an {@code event} is propagated to {@code handlers}.
     *
     * @param event the event to propagate to {@code handlers}
     * @param timing the {@link EventType} of the {@code event}
     * @param handlers the {@link NavigableSet}&lt;{@link HandlerEncapsulator}&gt; that have been invoked
     */
    default void postFireEvent(T event, EventType timing, NavigableSet<HandlerEncapsulator<T>> handlers) { }

    /**
     * Invoked when the handling of an {@code event} is skipped.
     *
     * <p>
     * Precisely, this is invoked when {@link EventManager#fireEvent(Object, EventType)} is invoked with an {@code event} and
     * {@code timing} that leads to a {@code NULL} or {@code EMPTY} {@link NavigableSet}&lt;{@link HandlerEncapsulator}&gt;s.
     * </p>
     *
     * @param event the event that has been skipped
     * @param timing the {@link EventType} of the {@code event}
     */
    default void onSkippedEvent(T event, EventType timing) { }
}
