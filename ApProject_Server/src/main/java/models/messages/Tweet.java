package models.messages;






import models.auth.User;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Tweet extends Message {


     private List<Integer> commentsId;
     private String tweetTopic;

     public Tweet(String messageType, String text, User user,
                  LocalDateTime localDateTime, String TweetTopic) {
          super(messageType, text, user, localDateTime);
          this.tweetTopic = TweetTopic;
          this.commentsId = new LinkedList<>();
     }

     public List<Integer> getCommentsId(){
          return commentsId;
     }

     public String getTweetTopic() {
          return tweetTopic;
     }

     public void setTweetTopic(String tweetTopic) {
          this.tweetTopic = tweetTopic;
     }

     public void setCommentsId(List<Integer> commentsId) {
          this.commentsId = commentsId;
     }

     @Override
     public Tweet clone() throws CloneNotSupportedException {
          return (Tweet) super.clone();
     }
}