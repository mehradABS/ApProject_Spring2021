package events.visitors.commentPage;

import events.visitors.EventVisitor;
import responses.Response;

public interface CommentPageEventVisitor extends EventVisitor {

      Response loadOriginalTweet(int tweetId, String type, String visitorType);
      Response loadComments(int originalId, String type, String visitorType);
}
