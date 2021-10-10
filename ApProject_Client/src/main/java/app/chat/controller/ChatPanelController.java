package app.chat.controller;

import controller.OfflineController;
import db.UserDB;
import models.OChat;
import models.auth.OUser;
import models.messages.OMessage;
import network.ImageSender;
import resources.Texts;
import responses.chat.DeleteMessageResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ChatPanelController extends OfflineController {

    public void setUserAndMessages(OChat chat, List<OMessage> messages,
                                   OUser user){
        try {
            CURRENT_USER = user;
            context.getUsers().set(CURRENT_USER);
            context.getChats().set(chat);
            for (OMessage msg: messages) {
                context.getMessages().set(msg, "message");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String[]> loadMessages(int chatId) throws IOException {
        List<String[]> allInfo = new LinkedList<>();
        OChat chat = context.getChats().get(chatId);
        List<OMessage> messages = new LinkedList<>();
        OUser current = CURRENT_USER;
        int indexOfChatId = 0;
        for (int i = 0; i < current.getMyChatIds().size(); i++) {
            if(current.getMyChatIds().get(i) == chatId){
                indexOfChatId = i;
                break;
            }
        }
        for (int i = 0; i < current.getMyUnreadChatIds().size(); i++) {
            if(current.getMyUnreadChatIds().get(i) == chatId){
                current.getMyUnreadChatMessagesNumbers().remove(i);
                current.getMyUnreadChatIds().remove(i);
                break;
            }
        }
        for (Integer id: chat.getId_Message()) {
            messages.add(context.getMessages().get(id, "message"));
        }
        for (Integer id: chat.getId_forwardMessage()) {
            messages.add(context.getMessages().get(id, "message"));
        }
        for (Integer id: chat.getForwardTweets()) {
            messages.add(context.getMessages().get(id, "message"));
        }
        for (Integer id: chat.getForwardComments()) {
            messages.add(context.getMessages().get(id, "message"));
        }
        OChat.sortOMessages(messages);
        for (int i = 0; i < messages.size(); i++) {
            String[] info;
            if(chat.getId_Message().contains(messages.get(i).getId())){
                boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                boolean f2 = current.getMyChatMessagesIds().get(indexOfChatId)
                        .contains(messages.get(i).getId());
                info = setMessageInfo(messages.get(i),
                        f && f2, f && f2, false, false);
                info[info.length - 1] = "message";
            }
            else if(chat.getId_forwardMessage().
                    contains(messages.get(i).getId())){
                boolean f = !chat.getDeletedMessage()
                        .contains(messages.get(i).getId());
                boolean f2 = current.getMyChatMessagesIds().get(indexOfChatId)
                        .contains(messages.get(i).getId());
                info = setMessageInfo(messages.get(i),
                        f && f2, false, true, false);
                info[info.length - 1] = "message";
            }
            else if(chat.getForwardTweets().contains(messages.get(i).getId())){
                boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                if(f) {
                    info = setTweetInfo(messages.get(i));
                    info[info.length - 1] = "tweet";
                }
                else{
                    OMessage message = context.getMessages().get(messages.get(i).getId(),
                            "message");
                    info = setMessageInfo(message, false, false
                            ,true, true);
                    info[info.length - 1] = "message";
                }
            }
            else{
                boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                if(f) {
                    info = setTweetInfo(messages.get(i));
                    info[info.length - 1] = "comment";
                }
                else{
                    OMessage message = context.getMessages().get(messages.get(i).getId(),
                            "message");
                    info = setMessageInfo(message, false, false
                            ,true, true);
                    info[info.length - 1] = "message";
                }
            }
            allInfo.add(info);
        }
        context.getUsers().set(current);
        return allInfo;
    }

    public String[] setMessageInfo(OMessage message, boolean deletable,
                                   boolean editable
            , boolean forward, boolean deletedTweet)
            throws IOException {
        OUser user;
        String[] info = new String[10];
        if(!forward){
            user = context.getUsers().get(message.getUserId());
            info[1] = message.getText();
        }
        else {
            user = context.getUsers().get(message.getForwarderId());
            info[1] = "forwarded from: " +
                    context.getUsers().
                            get(message.getUserId()).getAccount().getUsername()+"\n"+
                    message.getText();
        }
        info[0] = String.valueOf(deletable);
        if(!deletedTweet) {
            info[2] = context.getMessages().loadMessageImage(message.getId());
            if(forward){
                info[2] = context.getMessages().loadMessageImage(message.getImageId());
            }
        }
        else {info[2] = "null";}
        info[3] = user.getAccount().getUsername();
        info[4] = message.getLocalDateTime().getHour()+": "+message.getLocalDateTime()
                .getMinute();
        info[5] = String.valueOf(editable);
        info[6] = ((UserDB)context.getUsers()).loadProfile(message.getUserId(), 60);
        info[7] = String.valueOf(user.getId() == CURRENT_USER.getId());
        info[8] = String.valueOf(message.getId());
        return info;
    }

    public String[] setTweetInfo(OMessage message)
            throws IOException {
        OUser user = context.getUsers().get(message.getForwarderId());
        String[] info = loadTweetInfo(message, user,
                "");
        if(message.getForwarderId() != -1) {
            info[1] = context.getMessages().loadMessageImage(message.getImageId());
        }
        String[] info1 = new String[15];
        System.arraycopy(info, 0, info1, 0, 13);
        info1[13] = String.valueOf(user.getId() == CURRENT_USER.getId());
        return info1;
    }

    public <T extends OMessage>
    String[] loadTweetInfo(T tweet, OUser user, String retweetUsername) throws IOException {
        String [] info = new String[13];
        info[0] =retweetUsername + tweet.getText();
        File file = new File("src\\Save\\images\\tweets\\"+ tweet.getId() + ".png");
        if(file.exists()){
            info[1] = "src\\Save\\images\\tweets\\" + tweet.getId()+".png";
        }
        else {
            info[1] = "null";
        }
        String time = tweet.getLocalDateTime().getMonth().toString()
                +", "+tweet.getLocalDateTime().getDayOfMonth()+" -- "+
                tweet.getLocalDateTime().getHour()
                +": "+tweet.getLocalDateTime().getMinute();
        info[2] = "<html>"+user.getAccount().getUsername()+"<br>"+time+"</html>";
        File file1 = new File("src\\Save\\images\\"+
                (user.getId())
                +"\\profile\\profile60.png");
        if(file1.exists()){
            info[3] = "src\\Save\\images\\" +
                    (user.getId())
                    +"\\profile\\profile60.png";
            info[3] = ImageSender.encodeImage(info[3]);
        }
        else {
            info[3] = "def";
        }
        info[4] = "0";
        info[5] = "0";
        info[6] = String.valueOf(tweet.getId());
        info[7] = "false";
        info[8] = "0";
        info[9] = "false";
        info[10] = String.valueOf(CURRENT_USER.getId() == tweet.getUserId());
        if(tweet.getRetweet_userId() != null) {
            info[11] = String.valueOf(tweet.getRetweet_userId().size() - 1);
        }
        else{
            info[11] = "0";
        }
        info[12] = String.valueOf(user.getId());
        return info;
    }

    public void setMessage(OMessage msg) throws IOException {
        context.getMessages().set(msg, "message");
    }

    public void deleteMessage(DeleteMessageResponse response){
        try {
            context.getChats().set(response.getChat());
            context.getMessages().set(response.getMessage(), "message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] newMessage(String text, int chatId, LocalDateTime localDateTime) throws IOException {
        OUser current = context.getUsers().get(CURRENT_USER.getId());
        OMessage msg = new OMessage("message", text,
                current, localDateTime, context.getMessages().getAll("message")+1);
        OChat chat = context.getChats().get(chatId);
        chat.getId_Message().add(msg.getId());
        int a = 0;
        for (int i = 0; i < current.getMyChatIds().size(); i++) {
            if(current.getMyChatIds().get(i) == chat.getId()){
                a = i;
                break;
            }
        }
        current.getMyChatMessagesIds().get(a).add(msg.getId());
        context.getUsers().set(current);
        CURRENT_USER = current;
        context.getMessages().set(msg, "message");
        context.getChats().set(chat);
        return setMessageInfo
                (msg, true, true, false, false);
    }

    public void deleteMessage(int chatId, int messageId) throws IOException {
        OChat chat = context.getChats().get(chatId);
        if(chat.getId_Message().contains(messageId)
                || chat.getId_forwardMessage().contains(messageId)){
            context.getMessages().deleteMessageImage(messageId);
            OMessage msg = context.getMessages().get(messageId
                    ,"message");
            msg.setText(Texts.DELETED_MESSAGE);
            chat.getDeletedMessage().add(msg.getId());
            context.getMessages().set(msg, "message");
        }
        else if(chat.getForwardTweets().contains(messageId)){
            OMessage message = context.getMessages().get(messageId
                    ,"message");
            message.setText(Texts.DELETED_MESSAGE);
            chat.getDeletedMessage().add(message.getId());
            context.getMessages().set(message, "message");
        }
        else{
            OMessage message = context.getMessages().get(messageId
                    ,"message");
            message.setText(Texts.DELETED_MESSAGE);
            chat.getDeletedMessage().add(message.getId());
            context.getMessages().set(message, "message");
        }
        context.getChats().set(chat);
    }

    public void editMessage(int messageId, String text) throws IOException {
        OMessage message = context.getMessages().get(messageId,
                "message");
        message.setText(text);
        context.getMessages().set(message, "message");
    }
}