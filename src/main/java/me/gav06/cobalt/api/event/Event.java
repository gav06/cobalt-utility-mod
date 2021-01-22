package me.gav06.cobalt.api.event;

public class Event<T> {
    public boolean cancelled;
    public EventType type;
    public EventDirection direction;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventDirection getDirection() {
        return this.direction;
    }

    public void setDirection(EventDirection direction) {
        this.direction = direction;
    }

    public boolean isPre() {
        if (this.type == null) {
            return false;
        } else {
            return this.type == EventType.PRE;
        }
    }

    public boolean isPost() {
        if (this.type == null) {
            return false;
        } else {
            return this.type == EventType.POST;
        }
    }

    public boolean isIncoming() {
        if (this.direction == null) {
            return false;
        } else {
            return this.direction == EventDirection.INCOMING;
        }
    }

    public boolean isOutgoing() {
        if (this.direction == null) {
            return false;
        } else {
            return this.direction == EventDirection.OUTGOING;
        }
    }
}