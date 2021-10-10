package controller;

import app.auth.GetBioAndImageController;
import app.auth.LoginController;
import app.auth.RegisterController;
import app.chat.ChatPanelController;
import app.chat.ContactPanelController;
import app.chat.NewGroupController;
import app.explore.RandomTweetController;
import app.explore.search.SearchController;
import app.explore.watchProfile.WatchProfileController;
import app.list.ListController;
import app.personalPage.forwardMessage.ForwardController;
import app.personalPage.info.ChangeInfoController;
import app.personalPage.newPost.PostController;
import app.personalPage.tweetHistory.TweetHistoryController;
import app.personalPage.tweetHistory.newComment.CommentController;
import app.setting.SettingController;
import app.timeLine.TimelineController;
import app.timeLine.commentPage.CommentPageController;
import auth.AuthToken;
import events.Event;
import events.chat.SendMessageEvent;
import events.visitors.EventVisitor;
import events.visitors.OnlineEventVisitor;
import models.auth.Bot;
import models.auth.MessageSender;
import network.ResponseSender;
import notification.NotificationController;
import responses.ConnectionResponse;
import responses.GetPasswordResponse;
import responses.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class ClientHandler extends Thread implements OnlineEventVisitor {

    private final ResponseSender responseSender;
    private final HashMap<String, EventVisitor> visitors;
    private final AuthToken authToken;
    private int id;

    public ClientHandler(ResponseSender responseSender) {
        this.responseSender = responseSender;
        this.visitors = new HashMap<>();
        this.authToken = new AuthToken();
        setVisitors();
    }

    public void setVisitors() {
        visitors.put("SetProfileAndBioEventVisitor", new GetBioAndImageController(this));
        visitors.put("LoginEventVisitor", new LoginController(this));
        PostController postController = new PostController(this);
        visitors.put("NewTweetEventVisitor", postController);
        visitors.put("ChangeInfoEventVisitor", new ChangeInfoController(this));
        visitors.put("OnlineEventVisitor", this);
        TweetHistoryController tweetHistoryController =
                new TweetHistoryController(this);
        visitors.put("TweetHistoryEventsVisitor", tweetHistoryController);
        visitors.put("NewCommentEventVisitor", new CommentController(this));
        visitors.put("CommentPageEventVisitor", new CommentPageController(this
        , tweetHistoryController));
        visitors.put("TimeLineEventVisitor", new TimelineController(tweetHistoryController,
                this));
        visitors.put("TimeLineEventVisitor1", new RandomTweetController(tweetHistoryController,
                this));
        visitors.put("SearchEventVisitor", new SearchController(this));
        visitors.put("WatchProfileEventVisitor", new WatchProfileController(this));
        ContactPanelController contactPanelController =
                new ContactPanelController(this);
        visitors.put("ContactPanelEventVisitor", contactPanelController);
        visitors.put("NewGroupPanelEventVisitor", new NewGroupController(this, contactPanelController));
        ChatPanelController chatPanelController = new ChatPanelController(tweetHistoryController,
                this);
        visitors.put("RegistrationEventVisitor", new RegisterController(this));
        visitors.put("ChatMainPanelEventVisitor", chatPanelController);
        visitors.put("ListPanelEventVisitor", new ListController(chatPanelController , contactPanelController, this));
        visitors.put("SettingPanelEventVisitor", new SettingController(this));
        visitors.put("NotifEventVisitor", new NotificationController(this));
        visitors.put("ForwardMessageEventVisitor", new ForwardController(
                contactPanelController, this));
        visitors.put("logout", this);
        Bot.messageSender = chatPanelController::botMessageSender;
    }

    public void run(){
        try {
            while (true) {
                Event event = responseSender.getEvent();
                if (authToken.getAuthToken() == 0
                 || authToken.getAuthToken() == event.getAuthToken()) {
                    responseSender.sendResponse
                            (event.visit(visitors.get(event.getVisitorType())));
                }
                else{
                    responseSender.sendResponse(null);
                }
            }
        } catch (NoSuchElementException e) {
            MainController.removeClient(authToken.getAuthToken(), id);
            responseSender.close();
        }
    }

    public void setId(int id){
        this.id = id;
    }

    public AuthToken getAuthToken(){
        return authToken;
    }

    public int getClientId(){
        return id;
    }

    @Override
    public Response checkConnection(int clientId) {
        authToken.setAuthToken(MainController.addClient(clientId));
        setId(clientId);
        return new ConnectionResponse(authToken.getAuthToken());
    }

    public Response logout(){
        MainController.removeClient(authToken.getAuthToken(), id);
        authToken.setAuthToken(0);
        return null;
    }

    @Override
    public Response sendPassword() {
        try {
            return new GetPasswordResponse(MainController.
                    context.getUsers().get(id).getAccount()
                    .getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}