package models;



import models.messages.Message;
import models.messages.OMessage;

import java.util.LinkedList;
import java.util.List;

public class Chat extends OChat{

       public static final Object lock = new Object();
       private static int Id_counter;

       public Chat(){
              super(Id_counter + 1);
              Id_counter++;
       }

       public void setLists(){
              messages = new LinkedList<>();
              forwardMessages = new LinkedList<>();
       }

       public void setMessages(List<Message> messages) {
              this.messages = messages;
       }

       public static int getId_counter() {
              return Id_counter;
       }

       transient private List<Message> messages;

       public List<Message> getMessages() {
              return messages;
       }

       public List<Message> getForwardMessages() {
              return forwardMessages;
       }

       transient private  List<Message> forwardMessages;

       public static void setId_counter(int id_counter) {
              Id_counter = id_counter;
       }

       public void setForwardMessages(List<Message> forwardMessages) {
              this.forwardMessages = forwardMessages;
       }

       public static void sortMessages(List<Message> publicTweets){
              for (int i = 1; i < publicTweets.size(); i++) {
                     for (int j = i; j >0 ; j--) {
                            if(publicTweets.get(j).getLocalDateTime()
                            .isBefore(publicTweets.get(j-1).getLocalDateTime())){
                                   Message tweet= publicTweets.get(j);
                                   publicTweets.set(j,publicTweets.get(j-1));
                                   publicTweets.set(j-1,tweet);
                            }
                     }
              }
       }
}