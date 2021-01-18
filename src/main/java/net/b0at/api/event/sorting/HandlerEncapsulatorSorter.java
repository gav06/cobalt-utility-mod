package net.b0at.api.event.sorting;

import net.b0at.api.event.Subscribe;
import net.b0at.api.event.cache.HandlerEncapsulator;
import net.b0at.api.event.types.EventPriority;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Objects;

/**
 * The {@link Comparator} used to sort {@link HandlerEncapsulator}s based on their {@link EventPriority}.
 */
public class HandlerEncapsulatorSorter<T> implements Comparator<HandlerEncapsulator<T>> {
    /**
     * Sorts {@link HandlerEncapsulator}s based on their {@link EventPriority}.
     *
     * <p>
     * Precisely, this allows for the {@link NavigableSet}&lt;{@link HandlerEncapsulator}&gt;s to be sorted with respect
     * to the {@link Subscribe}'s {@link EventPriority}. This exact implementation causes new entries in the set to be
     * inserted at the end of the section occupied with the same {@link EventPriority}.
     * </p>
     *
     * @param a the first {@link HandlerEncapsulator} used in the comparison
     * @param b the second {@link HandlerEncapsulator} used in the comparison
     * @return 0 if these {@link HandlerEncapsulator}s are the equal (to ensure uniqueness), 1 if {@code a} has a lower priority than {@code b},
     * -1 if {@code a} has a higher priority than {@code b}, otherwise, compare {@link HandlerEncapsulator#hashCode()}
     */
    @Override
    public int compare(HandlerEncapsulator<T> a, HandlerEncapsulator<T> b) {
        // ensure the GENERAL CONTRACT is always upheld to the highest standard
        if (Objects.equals(a, b))
            return 0;

        if (a.getPriority().ordinal() == b.getPriority().ordinal())
            return Integer.compare(a.hashCode(), b.hashCode());

        if (a.getPriority().ordinal() > b.getPriority().ordinal())
            return 1;

        return -1;
    }
}
