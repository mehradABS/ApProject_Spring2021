package events.visitors.timeLine;

import events.visitors.EventVisitor;
import responses.Response;

public interface TimeLineEventVisitor extends EventVisitor {

    Response loadTweets();
}
