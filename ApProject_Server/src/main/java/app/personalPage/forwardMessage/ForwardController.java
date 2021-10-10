package app.personalPage.forwardMessage;

import app.chat.ContactPanelController;
import controller.ClientHandler;
import controller.MainController;
import events.forwardMessage.ForwardMessageEvent;
import events.forwardMessage.ForwardMessageEventVisitor;
import models.Chat;
import models.auth.User;
import models.messages.Message;
import models.messages.Tweet;
import responses.Response;
import responses.chat.LoadChatResponse;
import responses.forwardMessage.ForwardMessageResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ForwardController extends MainController implements ForwardMessageEventVisitor {

    private final ContactPanelController chatController;
    private final ClientHandler clientHandler;

    public ForwardController(ContactPanelController chatController,
                             ClientHandler clientHandler) {
        this.chatController = chatController;
        this.clientHandler = clientHandler;
    }

    public Response getEvent(ForwardMessageEvent event){
        try {
            if("loadFollowing".equals(event.getEvent())){
                ForwardMessageResponse response = new ForwardMessageResponse();
                response.setInfo(loadFollowings());
                response.setVisitorType(event.getResponseVisitorType());
                return response;
            }
            else if("sendMessage".equals(event.getEvent())){
                sendMessage(event.getIds(), event.getMessageId(), event.getMessageType());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(List<Integer> ids, int messageId, String type)
            throws IOException {
        synchronized (User.LOCK) {
            synchronized (Chat.lock) {
                synchronized (Message.LOCK) {
                    User current = context.getUsers().get(clientHandler.getClientId());
                    Message.setId_counter(context.getMessages().getIDCounter());
                    Message forwardMessage = context.getMessages().get(messageId, type);
                    User user1 = context.getUsers().get(forwardMessage.getUserId());
                    String text = "forwarded from: " + user1.getAccount().getUsername() + "\n" +
                            forwardMessage.getText();
                    Tweet message = new Tweet(
                            "forward", text, user1,
                            LocalDateTime.now(), "hi");
                    message.setImageId(messageId);
                    context.getMessages().setIDCounter(Message.getId_counter());
                    message.setForwarderId(clientHandler.getClientId());
                    context.getMessages().set(message, type);
                    for (Integer id : ids) {
                        Chat chat = context.getChats().get(id);
                        if (type.equals("tweet")) {
                            chat.getForwardTweets().add(message.getId());
                        } else {
                            chat.getForwardComments().add(message.getId());
                        }
                        int a = 0;
                        for (int i = 0; i < current.getMyChatIds().size(); i++) {
                            if (current.getMyChatIds().get(i) == chat.getId()) {
                                a = i;
                                break;
                            }
                        }
                        current.getMyChatMessagesIds().get(a).add(message.getId());
                        for (Integer userId : chat.getUsersIds()) {
                            if (userId != clientHandler.getClientId()) {
                                User user = context.getUsers().get(userId);
                                int b = 0;
                                boolean f = true;
                                for (int i = 0; i < user.getMyUnreadChatIds().size(); i++) {
                                    if (user.getMyUnreadChatIds().get(i) == chat.getId()) {
                                        b = i;
                                        f = false;
                                        break;
                                    }
                                }
                                if (!f) {
                                    int c = user.getMyUnreadChatMessagesNumbers().get(b);
                                    user.getMyUnreadChatMessagesNumbers().set(b, c + 1);
                                } else {
                                    user.getMyUnreadChatIds().add(chat.getId());
                                    user.getMyUnreadChatMessagesNumbers().add(1);
                                }
                                context.getUsers().set(user);
                            }
                        }
                        context.getChats().set(chat);
                    }
                    context.getUsers().set(current);
                }
            }
        }
    }

    public List<String[]> loadFollowings() {
        return ((LoadChatResponse)chatController.loadChats()).getChatsInfo();
    }
}