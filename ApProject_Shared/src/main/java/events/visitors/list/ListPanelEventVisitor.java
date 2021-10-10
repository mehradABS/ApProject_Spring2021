package events.visitors.list;

import events.list.ListPanelEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface ListPanelEventVisitor extends EventVisitor {

    Response getEvent(ListPanelEvent event);
}
