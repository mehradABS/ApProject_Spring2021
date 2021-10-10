package Models;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Tweet extends Message {
     transient private  List<Comment> comments;

     public void setComments(List<Comment> comments) {
          this.comments = comments;
     }

     private final List<Integer> commentsId;
     private String tweetTopic;

     public List<Integer> getCommentsId(){
          return commentsId;
     }
     public List<Comment> getComments() {
          return comments;
     }

     public Tweet(String messageType, String text, User user,
                  LocalDateTime localDateTime, String TweetTopic) {
          super(messageType, text, user, localDateTime);

          this.tweetTopic=TweetTopic;
          this.comments=new LinkedList<>();
          this.commentsId=new LinkedList<>();
     }

     public String getTweetTopic() {
          return tweetTopic;
     }

     public void setTweetTopic(String tweetTopic) {
          this.tweetTopic = tweetTopic;
     }
}