package app.explore;


import app.timeLine.TimelinePage;
import events.timeLine.LoadTimeLineTweetsEvent;
import network.EventListener;
import responses.visitors.ResponseVisitor;
import view.postView.commentPage.CommentView;

import java.util.HashMap;
import java.util.function.Supplier;

public class RandomTweetPanel extends TimelinePage {

    public RandomTweetPanel(Supplier<? extends CommentView> ctor
    , HashMap<String, ResponseVisitor> visitors, EventListener eventListener
    , String type, String newCommentResponseVisitorType) {
        super(ctor, eventListener, visitors, type, newCommentResponseVisitorType);
    }

    public void loadTweets(){
        LoadTimeLineTweetsEvent event = new LoadTimeLineTweetsEvent();
        event.setResponseVisitorType("TimeLineEventVisitor1");
        eventListener.listen(event);
    }
}