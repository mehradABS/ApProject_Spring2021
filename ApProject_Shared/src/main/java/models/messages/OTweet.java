package models.messages;





import models.auth.OUser;

import java.time.LocalDateTime;

public class OTweet extends OMessage {

     public OTweet(String messageType, String text, OUser user,
                   LocalDateTime localDateTime, int id) {
          super(messageType, text, user, localDateTime, id);
     }
}