package responses.visitors.commentPage;

import responses.commentPage.GetOriginalTweetResponse;
import responses.visitors.ResponseVisitor;

public interface CommentPageResponseVisitor extends ResponseVisitor {
    void getOriginalTweet(GetOriginalTweetResponse getOriginalTweetResponse);
}