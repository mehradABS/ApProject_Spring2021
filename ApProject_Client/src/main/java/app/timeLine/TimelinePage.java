package app.timeLine;

import app.personalPage.subPart.tweetHistory.TweetHistoryPanel;
import events.timeLine.LoadTimeLineTweetsEvent;
import network.EventListener;
import responses.visitors.ResponseVisitor;
import view.postView.commentPage.CommentView;

import java.util.HashMap;
import java.util.function.Supplier;

public class TimelinePage extends TweetHistoryPanel<CommentView> {

    public TimelinePage(Supplier<? extends CommentView> ctor, EventListener eventListener,
                        HashMap<String, ResponseVisitor> visitors,
                        String type, String newCommentResponseVisitorType) {
        super(ctor, eventListener, visitors, type, newCommentResponseVisitorType);
    }

    public void loadTweets(){
        LoadTimeLineTweetsEvent event = new LoadTimeLineTweetsEvent();
        event.setResponseVisitorType("TimeLineEventVisitor");
        eventListener.listen(event);
    }
}
