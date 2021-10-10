package Cli.agent;

import Logic.LogicalAgent;
import Models.Chat;
import Models.Log;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SendMessageAgent {
    public void sendMessage(User sender, User receiver, Message message,
                            boolean IsForward,
                            LogicalAgent logicalagent) throws IOException {
        boolean IsNewChat = true;
        for (Chat chat : sender.getMyChats().keySet()) {
            if (chat.getMembers().contains(receiver.getAccount().getUsername())) {
                IsNewChat = false;
                sender.getMyChats().get(chat).add(message);
                chat.getMessages().add(message);
                chat.getId_Message().add(message.getId());
                if (IsForward) {
                    chat.getForwardMessages().add(message);
                    chat.getId_forwardMessage().add(message.getId());
                }
                receiver.getUnreadChats().get(chat).add(message);
                receiver.getUnreadMessage().add(message.getId());
                logicalagent.getModelLoader().setChatChanges(chat);
                logicalagent.getModelLoader().setUserChanges(receiver);
                break;
            }
        }
        if (IsNewChat) {
            Chat chat = new Chat();
            logicalagent.getProgram().getAllChats().add(chat);
            chat.getMembers().add(sender.getAccount().getUsername());
            chat.getMembers().add(receiver.getAccount().getUsername());
            chat.getMessages().add(message);
            chat.getId_Message().add(message.getId());
            if (IsForward) {
                chat.getForwardMessages().add(message);
                chat.getId_forwardMessage().add(message.getId());
            }
            List<Message> senderMessages = new LinkedList<>();
            senderMessages.add(message);
            sender.getMyChats().put(chat, senderMessages);
            sender.getChatID().add(chat.getId());
            sender.getUnreadChats().put(chat, new LinkedList<>());
            receiver.getMyChats().put(chat, new LinkedList<>());
            receiver.getChatID().add(chat.getId());
            List<Message> unreadMessage = new LinkedList<>();
            unreadMessage.add(message);
            receiver.getUnreadMessage().add(message.getId());
            receiver.getUnreadChats().put(chat, unreadMessage);
            logicalagent.getModelLoader().setChatID_counter(Chat.getId_counter());
            logicalagent.getModelLoader().setChatChanges(chat);
            logicalagent.getModelLoader().setUserChanges(sender);
            logicalagent.getModelLoader().setUserChanges(receiver);
        }
        Log log=new Log("sent a message", LocalDateTime.now(),
                2,logicalagent.getProgram().getCurrentUser().getAccount()
                .getUsername());
        Log.log(log);
    }
}
