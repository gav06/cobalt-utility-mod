package net.b0at.api.event;

import net.b0at.api.event.cache.HandlerEncapsulator;
import net.b0at.api.event.cache.HandlerEncapsulatorWithTiming;
import net.b0at.api.event.exceptions.ListenerAlreadyRegisteredException;
import net.b0at.api.event.exceptions.ListenerNotAlreadyRegisteredException;
import net.b0at.api.event.profiler.IEventProfiler;
import net.b0at.api.event.sorting.HandlerEncapsulatorSorter;
import net.b0at.api.event.types.EventPriority;
import net.b0at.api.event.types.EventType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;

/**
 * The main class that handles {@link Subscribe} (de)registration and {@code event} firing.
 *
 * @param <T> the event base class of this {@link EventManager}
 */
@SuppressWarnings("WeakerAccess")
public final class EventManager<T> {
    /**
     * The {@link Comparator} used to sort {@link HandlerEncapsulator}s based on their {@link EventPriority}.
     */
    private final Comparator<HandlerEncapsulator<T>> ENCAPSULATOR_SORTER = new HandlerEncapsulatorSorter<>();

    /**
     * The active {@link IEventProfiler} for this {@link EventManager}.
     *
     * @see #setEventProfiler(IEventProfiler)
     */
    private IEventProfiler<T> eventProfiler = new IEventProfiler<T>() { };

    /**
     * The complex data structure holding {@link HandlerEncapsulator}s to be invoked.
     *
     * <p>
     * The {@link HandlerEncapsulator}s to be invoked are looked up based on the {@link EventType} and
     * the {@link Class} of the event.
     * <br>
     * The {@link HandlerEncapsulator}s are stored in a sorted {@link ConcurrentSkipListSet}.
     * </p>
     *
     * @see #ENCAPSULATOR_SORTER
     */
    private Map<EventType, Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>>> eventEncapsulatorMap = new HashMap<>();

    /**
     * The cache indicating if a {@code listener} has been discovered
     *
     * @see #discoverEventHandlers(Object)
     */
    private Set<Object> discoveredListeners = new HashSet<>();

    /**
     * The cache indicating if the {@link Subscribe#persistent()} handlers are registered (TRUE) or not (FALSE OR NULL).
     */
    private Map<Object, Boolean> listenerPersistentStates = new HashMap<>();

    /**
     * The cache indicating if the non {@link Subscribe#persistent()} handlers are registered (TRUE) or not (FALSE OR NULL).
     */
    private Map<Object, Boolean> listenerNonPersistentStates = new HashMap<>();

    /**
     * The cache of the {@link Subscribe#persistent()} {@link HandlerEncapsulator}s of the discovered listeners.
     */
    private Map<Object, Set<HandlerEncapsulator<T>>> persistentCache = new HashMap<>();

    /**
     * The cache of the non {@link Subscribe#persistent()} {@link HandlerEncapsulator}s of the discovered listeners.
     */
    private Map<Object, Set<HandlerEncapsulator<T>>> nonPersistentCache = new HashMap<>();

    /**
     * The number of {@link Method}s currently registered to receive events of all types.
     */
    private int registeredListenerCount = 0;

    /**
     * The active {@code exception hook} for this {@link EventManager}.
     *
     * @see #setExceptionHook(Consumer)
     */
    private Consumer<Exception> exceptionHook;

    /**
     * The event base class of this {@link EventManager}, typically <i>{@link Event}.class</i> or <i>{@link Object}.class</i>.
     */
    private final Class<T> BASE_CLASS;

    /**
     * Constructs a new {@link EventManager} with {@code BASE_CLASS} as the event base class.
     *
     * @param baseClass the base event class
     */
    public EventManager(Class<T> baseClass) {
        this.BASE_CLASS = baseClass;
        this.eventEncapsulatorMap.put(EventType.PRE, new HashMap<>());
        this.eventEncapsulatorMap.put(EventType.POST, new HashMap<>());
    }

    /**
     * Registers all applicable {@link Subscribe}s contained in the {@code listener} instance with this {@link EventManager}.
     *
     * @param listener the instance of the {@code listener} to register
     * @param onlyAddPersistent if this is TRUE, only {@link Subscribe#persistent()} are registered,
     *                          if this is FALSE, only non {@link Subscribe#persistent()} are registered
     * @throws ListenerAlreadyRegisteredException if this instance of a {@code listener} is already registered
     */
    public void registerListener(Object listener, boolean onlyAddPersistent) throws ListenerAlreadyRegisteredException {
        Map<Object, Boolean> listenerStates = onlyAddPersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
        Boolean state = listenerStates.get(listener);

        if (state == Boolean.TRUE) {
            throw new ListenerAlreadyRegisteredException(listener);
        }

        if (!this.discoveredListeners.contains(listener)) {
            this.discoverEventHandlers(listener);
        }

        listenerStates.put(listener, Boolean.TRUE);

        Set<HandlerEncapsulator<T>> encapsulatorSet = onlyAddPersistent ? this.persistentCache.get(listener) : this.nonPersistentCache.get(listener);
        for (HandlerEncapsulator<T> encapsulator : encapsulatorSet) {
            encapsulator.setEnabled(true);
        }
        this.registeredListenerCount += encapsulatorSet.size();
        this.eventProfiler.onRegisterListener(listener, onlyAddPersistent);
    }

    /**
     * Registers all non {@link Subscribe#persistent()} {@link Subscribe}s contained in the {@code listener} instance with this {@link EventManager}.
     *
     * @see #registerListener(Object, boolean)
     *
     * @param listener the instance of the {@code listener} to register
     * @throws ListenerAlreadyRegisteredException if this instance of a {@code listener} is already registered
     */
    public void registerListener(Object listener) throws ListenerAlreadyRegisteredException {
        this.registerListener(listener, false);
    }

    /**
     * Performs a (very expensive) scan for potential {@link Subscribe}s in the {@code listener} object.
     *
     * <p>
     * The persistent {@link Subscribe}s and the non-persistent {@link Subscribe}s found are stored
     * in {@link #persistentCache} and {@link #nonPersistentCache} respectively.
     * </p>
     *
     * @see #discoveredListeners
     * @see #persistentCache
     * @see #nonPersistentCache
     *
     * @param listener the instance of the {@code listener} to scan for {@link Subscribe}s
     */
    private void discoverEventHandlers(Object listener) {
        this.eventProfiler.preListenerDiscovery(listener);
        Set<HandlerEncapsulator<T>> persistentSet = new HashSet<>();
        Set<HandlerEncapsulator<T>> nonPersistentSet = new HashSet<>();

        this.discoveredListeners.add(listener);
        this.persistentCache.put(listener, persistentSet);
        this.nonPersistentCache.put(listener, nonPersistentSet);

        int methodIndex = 0;
        Class<?> clazz = listener.getClass();

        while (clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                if ((method.getModifiers() & Modifier.PRIVATE) != 0) {
                    continue;
                }
                if ((method.getParameterCount() == 1 || (method.getParameterCount() == 2 && EventType.class.isAssignableFrom(method.getParameterTypes()[1])))
                        && method.isAnnotationPresent(Subscribe.class) && this.BASE_CLASS.isAssignableFrom(method.getParameterTypes()[0])) {
                    boolean includesTimingParam = method.getParameterCount() == 2;

                    @SuppressWarnings("unchecked")
                    Class<? extends T> eventClass = (Class<? extends T>) method.getParameterTypes()[0];
                    Subscribe eventHandler = method.getAnnotation(Subscribe.class);

                    HandlerEncapsulator<T> encapsulator;

                    if (includesTimingParam) {
                        NavigableSet<HandlerEncapsulator<T>> preSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(EventType.PRE), eventClass);
                        NavigableSet<HandlerEncapsulator<T>> postSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(EventType.POST), eventClass);

                        encapsulator = new HandlerEncapsulatorWithTiming<>(listener, method, methodIndex, eventHandler.priority(), preSet, postSet);
                    } else {
                        NavigableSet<HandlerEncapsulator<T>> navigableSet = this.getOrCreateNavigableSet(this.eventEncapsulatorMap.get(eventHandler.timing()), eventClass);

                        encapsulator = new HandlerEncapsulator<>(listener, method, methodIndex, eventHandler.priority(), navigableSet);
                    }

                    Set<HandlerEncapsulator<T>> encapsulatorSet = eventHandler.persistent() ? persistentSet : nonPersistentSet;
                    encapsulatorSet.add(encapsulator);
                }
                methodIndex++;
            }

            clazz = clazz.getSuperclass();
        }

        this.eventProfiler.postListenerDiscovery(listener);
    }

    /**
     * Retrieves the {@link NavigableSet}&lt;{@link HandlerEncapsulator}&gt;
     * from the {@code encapsulatorMap} with the given {@code eventClass}.
     *
     * <p>
     * If this set is not found, a new {@link ConcurrentSkipListSet} is created and inserted into {@code encapsulatorMap}.
     * </p>
     *
     * @param encapsulatorMap the map that contains the {@link NavigableSet}s the or create the {@link NavigableSet} from
     * @param eventClass the {@link Class} to look up in the {@code encapsulatorMap}
     * @return the created or retrieved {@link NavigableSet} from the {@code encapsulatorMap}
     */
    private NavigableSet<HandlerEncapsulator<T>> getOrCreateNavigableSet(Map<Class<? extends T>, NavigableSet<HandlerEncapsulator<T>>> encapsulatorMap, Class<? extends T> eventClass) {
        NavigableSet<HandlerEncapsulator<T>> navigableSet = encapsulatorMap.get(eventClass);

        if (navigableSet == null) {
            navigableSet = new ConcurrentSkipListSet<>(ENCAPSULATOR_SORTER);
            encapsulatorMap.put(eventClass, navigableSet);
        }

        return navigableSet;
    }

    /**
     * Deregisters all applicable {@link Subscribe}s contained in the {@code listener} instance with this {@link EventManager}.
     *
     * @param listener the instance of the {@code listener} to deregister
     * @param onlyRemovePersistent if this is TRUE, only {@link Subscribe#persistent()} are deregistered,
     *                             if this is FALSE, only non {@link Subscribe#persistent()} are deregistered
     * @throws ListenerNotAlreadyRegisteredException if this instance of a {@code listener} is not already registered
     * <br>
     * This could occur if the {@code listener} was already deregistered, or if it was never registered.
     */
    public void deregisterListener(Object listener, boolean onlyRemovePersistent) throws ListenerNotAlreadyRegisteredException {
        Map<Object, Boolean> listenerStates = onlyRemovePersistent ? this.listenerPersistentStates : this.listenerNonPersistentStates;
        Boolean state = listenerStates.get(listener);

        // check if state is equal to null or equal to FALSE
        if (state != Boolean.TRUE) {
            throw new ListenerNotAlreadyRegisteredException(listener);
        }

        listenerStates.put(listener, Boolean.FALSE);
        Set<HandlerEncapsulator<T>> encapsulatorSet = onlyRemovePersistent ? this.persistentCache.get(listener) : this.nonPersistentCache.get(listener);
        for (HandlerEncapsulator<T> encapsulator : encapsulatorSet) {
            encapsulator.setEnabled(false);
        }
        this.registeredListenerCount -= encapsulatorSet.size();
        this.eventProfiler.onDeregisterListener(listener, onlyRemovePersistent);
    }

    /**
     * Deregisters all non {@link Subscribe#persistent()} {@link Subscribe}s contained in the {@code listener} instance with this {@link EventManager}.
     *
     * @see #deregisterListener(Object, boolean)
     *
     * @param listener the instance of the {@code listener} to deregister
     * @throws ListenerNotAlreadyRegisteredException if this instance of a {@code listener} is not already registered
     * <br>
     * This could occur if the {@code listener} was already deregistered, or if it was never registered.
     */
    public void deregisterListener(Object listener) throws ListenerNotAlreadyRegisteredException {
        this.deregisterListener(listener, false);
    }

    /**
     * This deregisters all registered listeners in this {@link EventManager}, disabling all {@link Subscribe}s.
     *
     * <p>
     * This also keeps all of the caches, so future registration of the same {@code listener}s are not expensive.
     * <br>
     * To explicitly clear all of the caches, consider {@link #cleanup()}.
     * </p>
     */
    public void deregisterAll() {
        this.eventEncapsulatorMap.get(EventType.PRE).values().forEach(Set::clear);
        this.eventEncapsulatorMap.get(EventType.POST).values().forEach(Set::clear);
        this.listenerPersistentStates.keySet().forEach(listener -> this.listenerPersistentStates.put(listener, Boolean.FALSE));
        this.listenerNonPersistentStates.keySet().forEach(listener -> this.listenerNonPersistentStates.put(listener, Boolean.FALSE));
        this.registeredListenerCount = 0;
        this.eventProfiler.onDeregisterAll();
    }

    /**
     * This cleans up this {@link EventManager}. This restores the {@link EventManager} to the default state.
     *
     * <p>
     * This invalidates all caches, disables all listeners, and calls {@link IEventProfiler#onCleanup()}.
     * <br>
     * This effectively creates a new {@link EventManager} instance, discarding the old one.
     * </p>
     */
    public void cleanup() {
        this.eventEncapsulatorMap.get(EventType.PRE).clear();
        this.eventEncapsulatorMap.get(EventType.POST).clear();

        this.discoveredListeners.clear();
        this.listenerPersistentStates.clear();
        this.listenerNonPersistentStates.clear();
        this.persistentCache.clear();
        this.nonPersistentCache.clear();

        this.registeredListenerCount = 0;
        this.eventProfiler.onCleanup();
    }

    /**
     * Returns the number of unique {@link Subscribe}s that are registered to receive events.
     *
     * @return the number of {@link Subscribe}s that are currently activated
     */
    public int getRegisteredListenerCount() {
        return this.registeredListenerCount;
    }
    
    /**
     * Fires an {@code event} to all eligible {@link Subscribe}s.
     *
     * <p>
     * This causes all applicable {@link Subscribe}s to be invoked with the given {@code event} object.
     * <br>
     * An applicable {@link Subscribe} is a {@link Method} where the first parameter has the same type as {@code event},
     * with either: {@link Subscribe#timing()} equal to {@link EventType#PRE}, or a {@link Method} where the second
     * parameter has the {@link EventType} type.
     * </p>
     *
     * @see #fireEvent(Object, EventType)
     * 
     * @param event the event instance to be propagated to all eligible {@link Subscribe}s, designated my this {@code event} type
     * @param <E> the type of the {@code event} fired, which must be a subclass of this {@link EventManager}'s {@link #BASE_CLASS}
     * @return the provided {@code event}
     */
    public <E extends T> E fireEvent(E event) {
        return this.fireEvent(event, EventType.PRE);
    }
    
    /**
     * Fires an {@code event} to all eligible {@link Subscribe}s.
     * 
     * <p>
     * This causes all applicable {@link Subscribe}s to be invoked with the given {@code event} object.
     * <br>
     * An applicable {@link Subscribe} is a {@link Method} where the first parameter has the same type as {@code event},
     * with either: {@link Subscribe#timing()} equal to {@code timing}, or a {@link Method} where the second
     * parameter has the {@link EventType} type.
     * </p>
     * 
     * @param event the event instance to be propagated to all eligible {@link Subscribe}s, designated my this {@code event} type and {@code timing}
     * @param timing the {@link EventType} of the {@code event}
     * @param <E> the type of the {@code event} fired, which must be a subclass of this {@link EventManager}'s {@link #BASE_CLASS}
     * @return the provided {@code event}
     */
    public synchronized <E extends T> E fireEvent(E event, EventType timing) {
        NavigableSet<HandlerEncapsulator<T>> encapsulatorSet = this.eventEncapsulatorMap.get(timing).get(event.getClass());

        if (encapsulatorSet == null || encapsulatorSet.isEmpty()) {
            this.eventProfiler.onSkippedEvent(event, timing);
        } else {
            this.eventProfiler.preFireEvent(event, timing, encapsulatorSet);

            for (HandlerEncapsulator<T> encapsulator : encapsulatorSet) {
                try {
                    encapsulator.invoke(event, timing);
                } catch (Exception e) {
                    if (this.exceptionHook == null) {
                        throw e;
                    }
                    this.exceptionHook.accept(e);
                }
            }

            this.eventProfiler.postFireEvent(event, timing, encapsulatorSet);
        }

        return event;
    }

    /**
     * Set the current {@link IEventProfiler} for this {@link EventManager}.
     *
     * @param eventProfiler the new {@link IEventProfiler}
     */
    public void setEventProfiler(IEventProfiler<T> eventProfiler) {
        this.eventProfiler = eventProfiler;
    }

    /**
     * Set the current exception hook for this {@link EventManager}.
     *
     * <p>
     * This {@link Consumer} will receive all exceptions raised in the execution of an event.
     * </p>
     *
     * @param exceptionHook the new {@link Consumer} to receive exceptions
     */
    public void setExceptionHook(Consumer<Exception> exceptionHook) {
        this.exceptionHook = exceptionHook;
    }
}
