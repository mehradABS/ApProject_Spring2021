package events;

import events.visitors.EventVisitor;
import responses.Response;

public abstract class Event{

    private int id;

    protected long authToken;

    public abstract Response visit(EventVisitor eventVisitor);

    public abstract String getVisitorType();

    public long getAuthToken() {
        return authToken;
    }

    public void setAuthToken(long authToken) {
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}