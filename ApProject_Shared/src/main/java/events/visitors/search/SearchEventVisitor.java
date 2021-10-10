package events.visitors.search;

import events.visitors.EventVisitor;
import responses.Response;

public interface SearchEventVisitor extends EventVisitor {

    Response findUser(String username);
}
