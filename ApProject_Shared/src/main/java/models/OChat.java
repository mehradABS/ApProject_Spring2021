package models;




import models.messages.OMessage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class OChat {

       protected final int id;

       protected boolean isGroupChat = false;

       protected final HashSet<Integer> usersIds;

       protected String name;

       protected final List<Integer> deletedMessage;

       protected final List<Integer> forwardTweets;

       protected final List<Integer> forwardComments;

       protected final List<Integer> id_Message;

       protected final List<Integer> id_forwardMessage;

       public void setLists(){
              oMessages = new LinkedList<>();
              forwardOMessages = new LinkedList<>();
       }

       public OChat(int id){
              this.id = id;
              id_forwardMessage = new LinkedList<>();
              id_Message = new LinkedList<>();
              usersIds = new HashSet<>();
              forwardComments = new LinkedList<>();
              forwardTweets = new LinkedList<>();
              deletedMessage = new LinkedList<>();
              setLists();
       }

       public List<Integer> getForwardTweets() {
              return forwardTweets;
       }

       public List<Integer> getForwardComments() {
              return forwardComments;
       }

       public List<Integer> getDeletedMessage() {
              return deletedMessage;
       }

       public boolean isGroupChat() {
              return isGroupChat;
       }

       public void setGroupChat(boolean groupChat) {
              isGroupChat = groupChat;
       }

       public int getId() {
              return id;
       }

       public void setoMessages(List<OMessage> oMessages) {
              this.oMessages = oMessages;
       }

       transient private List<OMessage> oMessages;

       public List<OMessage> getoMessages() {
              return oMessages;
       }

       public List<OMessage> getForwardOMessages() {
              return forwardOMessages;
       }

       transient private List<OMessage> forwardOMessages;

       public void setForwardOMessages(List<OMessage> forwardOMessages) {
              this.forwardOMessages = forwardOMessages;
       }

       public List<Integer> getId_Message(){
              return id_Message;
       }

       public List<Integer> getId_forwardMessage(){
              return id_forwardMessage;
       }

       public HashSet<Integer> getUsersIds() {
              return usersIds;
       }

       public String getName() {
              return name;
       }

       public void setName(String name) {
              this.name = name;
       }

       public static void sortOMessages(List<OMessage> publicTweets){
              for (int i = 1; i < publicTweets.size(); i++) {
                     for (int j = i; j >0 ; j--) {
                            if(publicTweets.get(j).getLocalDateTime()
                            .isBefore(publicTweets.get(j-1).getLocalDateTime())){
                                   OMessage tweet= publicTweets.get(j);
                                   publicTweets.set(j,publicTweets.get(j-1));
                                   publicTweets.set(j-1,tweet);
                            }
                     }
              }
       }
}