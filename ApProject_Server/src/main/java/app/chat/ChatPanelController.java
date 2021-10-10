package app.chat;

import controller.ClientHandler;
import events.chat.DeleteMessageEvent;
import events.chat.SendMessageEvent;
import events.visitors.chat.ChatMainPanelEventVisitor;
import models.Chat;
import models.auth.User;
import models.messages.Message;
import app.personalPage.tweetHistory.TweetHistoryController;
import controller.MainController;
import models.messages.OMessage;
import network.ImageSender;
import resources.Texts;
import responses.Response;
import responses.chat.DeleteMessageResponse;
import responses.chat.EditMessageResponse;
import responses.chat.LoadMessagesResponse;
import responses.chat.SendMessageResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChatPanelController extends MainController
        implements ChatMainPanelEventVisitor {

    private final TweetHistoryController tweetHistoryController;
    private final ClientHandler clientHandler;


    public ChatPanelController(TweetHistoryController tweetHistoryController,
                               ClientHandler clientHandler) {
        this.tweetHistoryController = tweetHistoryController;
        this.clientHandler = clientHandler;
    }

    public Response loadMessages(int chatId) {
        try {
            synchronized (User.LOCK) {
                List<String[]> allInfo = new LinkedList<>();
                Chat chat = context.getChats().get(chatId);
                List<Message> messages = new LinkedList<>();
                User current = context.getUsers().get(clientHandler.getClientId());
                int indexOfChatId = 0;
                for (int i = 0; i < current.getMyChatIds().size(); i++) {
                    if (current.getMyChatIds().get(i) == chatId) {
                        indexOfChatId = i;
                        break;
                    }
                }
                for (int i = 0; i < current.getMyUnreadChatIds().size(); i++) {
                    if (current.getMyUnreadChatIds().get(i) == chatId) {
                        current.getMyUnreadChatMessagesNumbers().remove(i);
                        current.getMyUnreadChatIds().remove(i);
                        break;
                    }
                }
                for (Integer id : chat.getId_Message()) {
                    messages.add(context.getMessages().get(id, "message"));
                }
                for (Integer id : chat.getId_forwardMessage()) {
                    messages.add(context.getMessages().get(id, "message"));
                }
                for (Integer id : chat.getForwardTweets()) {
                    messages.add(context.getMessages().get(id, "tweet"));
                }
                for (Integer id : chat.getForwardComments()) {
                    messages.add(context.getMessages().get(id, "comment"));
                }
                Chat.sortMessages(messages);
                for (int i = 0; i < messages.size(); i++) {
                    String[] info;
                    if (chat.getId_Message().contains(messages.get(i).getId())) {
                        boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                        boolean f2 = current.getMyChatMessagesIds().get(indexOfChatId)
                                .contains(messages.get(i).getId());
                        info = setMessageInfo(messages.get(i),
                                f && f2, f && f2, false, false);
                        info[info.length - 1] = "message";
                    } else if (chat.getId_forwardMessage().
                            contains(messages.get(i).getId())) {
                        boolean f = !chat.getDeletedMessage()
                                .contains(messages.get(i).getId());
                        boolean f2 = current.getMyChatMessagesIds().get(indexOfChatId)
                                .contains(messages.get(i).getId());
                        info = setMessageInfo(messages.get(i),
                                f && f2, false, true, false);
                        info[info.length - 1] = "message";
                    } else if (chat.getForwardTweets().contains(messages.get(i).getId())) {
                        boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                        if (f) {
                            info = setTweetInfo(messages.get(i));
                            info[info.length - 1] = "tweet";
                        } else {
                            Message message = context.getMessages().get(messages.get(i).getId(),
                                    "message");
                            info = setMessageInfo(message, false, false
                                    , true, true);
                            info[info.length - 1] = "message";
                        }
                    } else {
                        boolean f = !chat.getDeletedMessage().contains(messages.get(i).getId());
                        if (f) {
                            info = setTweetInfo(messages.get(i));
                            info[info.length - 1] = "comment";
                        } else {
                            Message message = context.getMessages().get(messages.get(i).getId(),
                                    "message");
                            info = setMessageInfo(message, false, false
                                    , true, true);
                            info[info.length - 1] = "message";
                        }
                    }
                    allInfo.add(info);
                }
                context.getUsers().set(current);
                List<OMessage> oMessages = new LinkedList<>(messages);
                return new LoadMessagesResponse(allInfo, oMessages, chat, current);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] setMessageInfo(Message message, boolean deletable,
                                   boolean editable
                            , boolean forward, boolean deletedTweet)
            throws IOException {
        User user;
        String[] info = new String[12];
        if(!forward){
            user = context.getUsers().get(message.getUserId());
            info[1] = message.getText();
        }
         else {
            user = context.getUsers().get(message.getForwarderId());
            info[1] = "forwarded from: " +
                    context.getUsers().
                            get(message.getUserId()).getAccount().getUsername() + "\n" +
                            message.getText();
         }
        info[0] = String.valueOf(deletable);
        if(!deletedTweet) {
            info[2] = context.getTweets().loadTweetImage(message.getId());
            info[9] = String.valueOf(message.getId());
            if(forward){
                info[2] = context.getTweets().loadTweetImage(message.getImageId());
                info[9] = String.valueOf(message.getImageId());
            }
            if(!info[2].equals("null")){
                info[2] = ImageSender.encodeImage(info[2]);
            }
        }
        else {info[2] = "null";}
        info[3] = user.getAccount().getUsername();
        info[4] = message.getLocalDateTime().getHour() + ": " + message.getLocalDateTime()
                .getMinute();
        info[5] = String.valueOf(editable);
        info[6] = ContactPanelController.loadImage(message.getUserId());
        info[7] = String.valueOf(user.getId() == clientHandler.getClientId());
        info[8] = String.valueOf(message.getId());
        info[10] = String.valueOf(message.getUserId());
        return info;
    }

    public String[] setTweetInfo(Message message)
            throws IOException {
        int k;
        User user = context.getUsers().get(message.getForwarderId());
        String[] info = tweetHistoryController.loadTweetInfo(message, user,
                "");
        k = Integer.parseInt(info[6]);
        if(message.getForwarderId() != -1) {
            info[1] = context.getTweets().loadTweetImage(message.getImageId());
            k = message.getImageId();
        }
        if(!info[1].equals("null")){
            info[1] = ImageSender.encodeImage(info[1]);
        }
        String[] info1 = new String[16];
        System.arraycopy(info, 0, info1, 0, 13);
        info1[13] = String.valueOf(user.getId() == clientHandler.getClientId());
        info1[14] = String.valueOf(k);
        return info1;
    }

    public Response newMessage(SendMessageEvent event) {
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    synchronized (Message.LOCK) {
                        User current = context.getUsers().get(clientHandler.getClientId());
                        Message.setId_counter(context.getMessages().getIDCounter());
                        Message msg = new Message("message", event.getText(),
                                current, event.getLocalDateTime());
                        Chat chat = context.getChats().get(event.getChatId());
                        chat.getId_Message().add(msg.getId());
                        int a = 0;
                        for (int i = 0; i < current.getMyChatIds().size(); i++) {
                            if (current.getMyChatIds().get(i) == chat.getId()) {
                                a = i;
                                break;
                            }
                        }
                        current.getMyChatMessagesIds().get(a).add(msg.getId());
                        for (Integer userId : chat.getUsersIds()) {
                            if (userId != clientHandler.getClientId()) {
                                User user = context.getUsers().get(userId);
                                user.receiveMessage(chat.getId(), event.getText(), clientHandler.getClientId());
                                context.getUsers().set(user);
                            }
                        }
                        if(!event.getEncodedImage().equals("null")) {
                            context.getMessages().setMessageImage(msg.getId(),
                                    event.getEncodedImage());
                        }
                        context.getUsers().set(current);
                        context.getMessages().setIDCounter(Message.getId_counter());
                        context.getMessages().set(msg, "message");
                        context.getChats().set(chat);
                        if(event.isOffline()){
                            return null;
                        }
                        SendMessageResponse response = new SendMessageResponse();
                        response.setChat(chat);
                        response.setUser(current);
                        response.setMessage(msg);
                        response.setInfo(setMessageInfo
                                (msg, true, true, false, false));
                        return response;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Response deleteMessage(DeleteMessageEvent chatFormEvent) {
        try {
            synchronized (Chat.lock) {
                synchronized (Message.LOCK) {
                    DeleteMessageResponse response = new DeleteMessageResponse();
                    Chat chat = context.getChats().get(chatFormEvent.getChatId());
                    if (chat.getId_Message().contains(chatFormEvent.getMessageId())
                            || chat.getId_forwardMessage().contains(chatFormEvent.getMessageId())) {
                        context.getMessages().deleteTweetImage(chatFormEvent.getMessageId());
                        Message msg = context.getMessages().get(chatFormEvent.getMessageId()
                                , "message");
                        msg.setText(Texts.DELETED_MESSAGE);
                        chat.getDeletedMessage().add(msg.getId());
                        context.getMessages().set(msg, "message");
                        response.setMessage(msg);
                    } else if (chat.getForwardTweets().contains(chatFormEvent.getMessageId())) {
                        Message message = context.getTweets().get(chatFormEvent.getMessageId()
                                , "tweet");
                        message.setText(Texts.DELETED_MESSAGE);
                        chat.getDeletedMessage().add(message.getId());
                        context.getMessages().set(message, "message");
                        response.setMessage(message);
                    } else {
                        Message message = context.getComments().get(chatFormEvent.getMessageId()
                                , "comment");
                        message.setText(Texts.DELETED_MESSAGE);
                        chat.getDeletedMessage().add(message.getId());
                        context.getMessages().set(message, "message");
                        response.setMessage(message);
                    }
                    deleteMessageErrorHandling(chatFormEvent.getMessageId());
                    response.setChat(chat);
                    response.setMessageId(chatFormEvent.getMessageId());
                    return response;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMessageErrorHandling(int msgId) throws IOException {
        int chatIdCounter = context.getChats().getIDCounter();
        for (int i = 1; i <= chatIdCounter; i++) {
            try {
                Chat chat = context.getChats().get(i);
                chat.getDeletedMessage().add(msgId);
                context.getChats().set(chat);
            }
            catch (FileNotFoundException ignored){

            }
        }
    }

    public Response editMessage(SendMessageEvent event) {
        try {
            synchronized (Message.LOCK) {
                Message message = context.getMessages().get(event.getChatId(),
                        "message");
                message.setText(event.getText());
                context.getMessages().set(message, "message");
                EditMessageResponse response = new EditMessageResponse();
                response.setMessage(message);
                return response;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void botMessageSender(SendMessageEvent event, int botId){
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    synchronized (Message.LOCK) {
                        User current = context.getUsers().get(botId);
                        Message.setId_counter(context.getMessages().getIDCounter());
                        Message msg = new Message("message", event.getText(),
                                current, event.getLocalDateTime());
                        Chat chat = context.getChats().get(event.getChatId());
                        chat.getId_Message().add(msg.getId());
                        int a = 0;
                        for (int i = 0; i < current.getMyChatIds().size(); i++) {
                            if (current.getMyChatIds().get(i) == chat.getId()) {
                                a = i;
                                break;
                            }
                        }
                        current.getMyChatMessagesIds().get(a).add(msg.getId());
                        for (Integer userId : chat.getUsersIds()) {
                            if (userId != botId) {
                                User user = context.getUsers().get(userId);
                                user.receiveMessage(chat.getId(), event.getText(), botId);
                                context.getUsers().set(user);
                            }
                        }
                        if(!event.getEncodedImage().equals("null")) {
                            context.getMessages().setMessageImage(msg.getId(),
                                    event.getEncodedImage());
                        }
                        context.getUsers().set(current);
                        context.getMessages().setIDCounter(Message.getId_counter());
                        context.getMessages().set(msg, "message");
                        context.getChats().set(chat);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}