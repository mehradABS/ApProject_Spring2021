package Models;

import java.util.LinkedList;
import java.util.List;

public class Chat {
       transient private  List<String> members;
       private static int Id_counter;
       public int getId() {
              return Id;
       }

       public void setMembers(List<String> members) {
              this.members = members;
       }

       public void setMessages(List<Message> messages) {
              this.messages = messages;
       }

       public static int getId_counter() {
              return Id_counter;
       }

       private final List<Integer> id_Message;
       private final List<Integer> id_forwardMessage;
       private final int Id;
       transient private  List<Message> messages;

       public List<String> getMembers() {
              return members;
       }

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

       public List<Integer> getId_Message(){
              return id_Message;
       }
       public List<Integer> getId_forwardMessage(){
              return id_forwardMessage;
       }
       public Chat(){
              Id_counter++;
              this.Id=Id_counter;
              members=new LinkedList<>();
              messages=new LinkedList<>();
              forwardMessages=new LinkedList<>();
              id_forwardMessage=new LinkedList<>();
              id_Message=new LinkedList<>();
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