package events.visitors.chat;

import events.visitors.EventVisitor;
import responses.Response;

public interface NewGroupPanelEventVisitor extends EventVisitor {

    Response loadFriends();
}
