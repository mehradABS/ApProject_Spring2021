package events.search;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.search.SearchEventVisitor;
import responses.Response;

public class SearchEvent extends Event {

    private String username;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((SearchEventVisitor)eventVisitor).findUser(username);
    }

    @Override
    public String getVisitorType() {
        return "SearchEventVisitor";
    }

    public void setUsername(String username) {
        this.username = username;
    }
}