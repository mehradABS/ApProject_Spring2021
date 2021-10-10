package app.chat;

import app.chat.controller.ChatPanelController;
import app.timeLine.CommentsPagePanel;
import controller.OfflineController;
import controller.OnlinePanels;
import db.UserDB;
import events.chat.DeleteMessageEvent;
import events.chat.EditMessageEvent;
import events.chat.LoadMessagesEvent;
import events.chat.SendMessageEvent;
import models.messages.OMessage;
import network.EventListener;
import network.ImageReceiver;
import resources.Colors;
import resources.Paths;
import responses.chat.DeleteMessageResponse;
import responses.chat.LoadMessagesResponse;
import responses.chat.SendMessageResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ChatMainPanelResponseVisitor;
import util.Loop;
import view.listeners.StringListener;
import view.postView.commentPage.CommentView;
import view.postView.message.MessageSenderPanel;
import view.postView.message.MessageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChatMainPanel extends JPanel implements OnlinePanels,
        ChatMainPanelResponseVisitor {

    private final ChatPanelController chatPanelController;
    private final AddPhotoPanel addPhotoPanel;
    private StringListener stringListener;
    private final CommentsPagePanel<CommentView> commentsPagePanel;
    private int height;
    private int chatId;
    private final List<JPanel> messages;
    private final MessageSenderPanel messageSenderPanel;
    private final JPanel upPanel;
    private final JScrollPane upPanelKeeper;
    private boolean currentPage = false;
    private final Loop loop = new Loop(1, this::loadMessages);
    private final EventListener eventListener;

    public ChatMainPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                         visitors) {
        visitors.put("ChatMainPanelResponseVisitor", this);
        this.eventListener = eventListener;
        chatPanelController = new ChatPanelController();
        messageSenderPanel = new MessageSenderPanel(620,800);
        OfflineController.ONLINE_PANELS.add(this);
        //
        upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanelKeeper = new JScrollPane(upPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        upPanelKeeper.getVerticalScrollBar().setUnitIncrement(16);
        upPanel.setBackground(Color.decode(Colors.SUB_PANEL));
        upPanelKeeper.setBounds(0,0,800,620);
        //
        addPhotoPanel = new AddPhotoPanel(eventListener, new HashMap<>(),"null");
        addPhotoPanel.setStringListener(text -> {
            if(text.equals("back")){
                remove(addPhotoPanel);
                addPhotoPanel.resetPanel();
                add(upPanelKeeper);
                loadMessages();
                repaint();
                revalidate();
            }
        });
        //
        commentsPagePanel = new CommentsPagePanel<>(CommentView::new,
                eventListener, visitors, "CommentPage4",
                "CommentPageResponseVisitor4"
                ,"confirmationNewCommentResponseVisitor7");
        //
        messageSenderPanel.setStringListener(text -> {
            if(text.equals("send")){
                remove(addPhotoPanel);
                addPhotoPanel.resetPanel();
                add(upPanelKeeper);
                sendAction();
            }
            else if(text.equals("photo")){
                remove(upPanelKeeper);
                add(addPhotoPanel);
                repaint();
                revalidate();
                addPhotoPanel.addPhotoButtonAction();
            }
            else {
                changeMessagesKeeperCoordinate(Integer.parseInt(text));
            }
        });
        //
        messages = new LinkedList<>();
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
        this.setBounds(680,50,800,700);
        this.add(upPanelKeeper);
        this.add(messageSenderPanel);
    }

    public void changeState(){
        if(currentPage)
            setInfo();
        else
            stopLoop();
    }

    public void setInfo(){
        currentPage = true;
        if(OfflineController.IS_ONLINE){
            loop.restart();
            System.out.println("salam");
        }
        else{
            loop.stop();
            try {
                makeMessages(chatPanelController.loadMessages(chatId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopLoop(){
        loop.stop();
        currentPage = false;
    }

    public void changeMessagesKeeperCoordinate(int y) {
        upPanelKeeper.setBounds(0,0,800,620 - y);
    }

    private void loadMessages() {
        eventListener.listen(new LoadMessagesEvent(chatId));
    }

    public void getMessages(LoadMessagesResponse response){
        List<String[]> info = response.getInfo();
        for (String[] fo: info) {
            if("message".equals(fo[fo.length - 1])){
                imageHandling(fo, 10, 6, 9, 2);
            }
        }
        chatPanelController.setUserAndMessages(response.getChat(), response.getChatMessages(),
                response.getUser());
        makeMessages(info);
    }

    public void makeMessages(List<String[]> info){
        try {
            messages.clear();
            height = 15;
            upPanel.removeAll();
            for (int i = 0; i < info.size(); i++) {
                setMessageInfo(info.get(i));
            }
            addMessages();
            repaint();
            revalidate();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//message: (user: id = 10, image = 6) (message: id = 9, image = 2)
//tweet: (user: id = 12, image = 3) (tweet: id = 14  , image = 1)

    public void imageHandling (String[] info, int userId, int userImage,
                               int messageId, int messageImage) {
        if(!info[messageImage].equals("null")){
            String localPath = Paths.TWEET_IMAGE_PATH + info[messageId] + ".png";
            ImageReceiver.decodeImage(info[messageImage], localPath);
            info[messageImage] = localPath;
        }
        if(info[userImage].equals("def")){
            info[userImage] = ((UserDB)OfflineController.getContext().getUsers()).loadProfile(
                    0, 60);
        }
        else{
            String outputImagePath2 = Paths.USER_IMAGE_PATH +
                    (info[userId])
                    +"\\profile\\profile100.png";
            String outputImagePath3 = Paths.USER_IMAGE_PATH +
                    (info[userId])
                    +"\\profile\\profile60.png";
            ImageReceiver.decodeImage(info[userImage], outputImagePath2);
            ImageReceiver.decodeImage(info[userImage], outputImagePath3);
            info[userImage] = outputImagePath3;
        }
    }

    public synchronized void setMessageInfo(String[] info) throws IOException {
        ImageIcon icon1;
        ImageIcon icon2;
        if(info[info.length - 1] .equals("message")){
            if(info[2].equals("null")){
              icon1 = null;
            }
            else{
                icon1 = new ImageIcon(ImageIO.read(new File(info[2])));
            }
            if(info[6].equals("null")){
                icon2 = null;
            }
            else{
                icon2 = new ImageIcon(ImageIO.read(new File(info[6])));
            }
            MessageView messageView = new MessageView();
            messageView.setInfo(info[1], icon1,
                     info[3], info[4], icon2);
            messageView.setMessageId(Integer.parseInt(info[8]));
            int x = messageView.getWidth();
            int increase;
            if(Boolean.parseBoolean(info[7]))
             {increase = messageView.setSizes(0,height) + 15;}
             else{increase = messageView.setSizes(790 - x, height) + 15;}
            messageView.setDeleteItem(Boolean.parseBoolean(info[0]));
            messageView.setEditItem(Boolean.parseBoolean(info[5]));
            messageView.addComponents();
            addStringListenerToMessageView(messageView);
            height += increase;
            messages.add(messageView);
        }
         else{
             CommentView commentView =
                     commentsPagePanel.makePostView(info, info[info.length - 1]);
             int increase = commentView.getHeight() + 15;
            if(Boolean.parseBoolean(info[13])){
                commentView.setSizes(0, height);
                commentView.setDeleteItem(true);
                commentView.addStringListener(text -> {
                       if(text.equals("delete")){
                           deleteTweet(commentView);
                       }
                });
            }
             else{
                 commentView.setSizes(95, height);
                 commentView.setDeleteItem(false);
            }
             height += increase;
             messages.add(commentView);
         }
    }

    public void addMessages(){
        upPanel.setPreferredSize(new Dimension(800,height));
        for (int i = messages.size() - 1; i >= 0; i--) {
            upPanel.add(messages.get(i));
        }
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void sendAction()  {
        boolean offline = !OfflineController.IS_ONLINE;
        if (messageSenderPanel.getEditableMessageId() == -1) {
            SendMessageEvent event = new SendMessageEvent();
            event.setText(messageSenderPanel.getTextArea().getText());
            event.setChatId(chatId);
            event.setEncodedImage(addPhotoPanel.getEncodedImage());
            event.setLocalDateTime(LocalDateTime.now());
            addPhotoPanel.setEncodedImage("null");
            eventListener.listen(event);
            if(offline){
                try {
                   String[] info = chatPanelController.newMessage(
                                   messageSenderPanel.getTextArea().getText(),chatId
                   , event.getLocalDateTime());
                   event.setOffline(true);
                   info[info.length - 1] = "message";
                   setMessageInfo(info);
                   upPanel.add(messages.get(messages.size() - 1));
                   upPanel.setPreferredSize(new Dimension(800, height));
                   repaint();
                   revalidate();
                   messageSenderPanel.resetPanel();
                   changeMessagesKeeperCoordinate(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            EditMessageEvent event = new EditMessageEvent();
            event.setText(messageSenderPanel.getTextArea().getText());
            event.setChatId(messageSenderPanel.getEditableMessageId());
            eventListener.listen(event);
            if(offline){
                try {
                    chatPanelController.editMessage(messageSenderPanel.getEditableMessageId(),
                            messageSenderPanel.getTextArea().getText());
                    messageSenderPanel.editText(false);
                    makeMessages(chatPanelController.loadMessages(chatId));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void newMessage(SendMessageResponse response) {
        String[] info = response.getInfo();
        info[info.length - 1] = "message";
        imageHandling(info, 10, 6, 9, 2);
        try {
            setMessageInfo(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        upPanel.add(messages.get(messages.size() - 1));
        upPanel.setPreferredSize(new Dimension(800, height));
        repaint();
        revalidate();
        messageSenderPanel.resetPanel();
        changeMessagesKeeperCoordinate(0);
        List<OMessage> messages = new LinkedList<>();
        messages.add(response.getMessage());
        chatPanelController.setUserAndMessages(response.getChat(),
                messages, response.getUser());
    }

    public void editMessage(SendMessageResponse response) {
        messageSenderPanel.editText(false);
        try {
            chatPanelController.setMessage(response.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(MessageView messageView) {
        DeleteMessageEvent event = new DeleteMessageEvent();
        event.setChatId(chatId);
        event.setMessageId(messageView.getMessageId());
        eventListener.listen(event);
        if(!OfflineController.IS_ONLINE){
            try {
                chatPanelController.deleteMessage(chatId, messageView.getMessageId());
                makeMessages(chatPanelController.loadMessages(chatId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void deleteTweet(CommentView commentView){
        DeleteMessageEvent event = new DeleteMessageEvent();
        event.setChatId(chatId);
        event.setMessageId(commentView.getTweetId());
        eventListener.listen(event);
        if(OfflineController.IS_ONLINE){
            try {
                chatPanelController.deleteMessage(chatId, commentView.getTweetId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteMessageResponse(DeleteMessageResponse response){
        chatPanelController.deleteMessage(response);
    }

    public void addStringListenerToMessageView(MessageView messageView){
        messageView.setStringListener(text -> {
            if(text.equals("edit")){
                messageSenderPanel.setEditableMessageId(messageView.getMessageId());
                messageSenderPanel.getTextArea().setText(messageView.getMessageText()
                .getText());
                messageSenderPanel.editText(true);
            }
            else if(text.equals("delete")){
                deleteMessage(messageView);
            }
        });
    }

    public void resetPanel(){
        remove(addPhotoPanel);
        try {
            addPhotoPanel.resetPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(upPanelKeeper);
        stopLoop();
        repaint();
        revalidate();
    }
}