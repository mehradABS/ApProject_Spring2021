package Cli.agent.BigAgent;


import Cli.*;
import Cli.agent.*;
import Logic.LogicalAgent;
import Models.*;


import java.io.IOException;

import java.util.List;

public class CliAgent {
    public Father father;
    Login login;
    LoginAgent loginAgent;
    LogicalAgent logicalagent;
    Menu menu;
    MenuAgent menuAgent;
    PersonalPage personalPage;
    PersonalPageAgent personalPageAgent;
    NewMessage newMessage;
    NewMessageAgent newMessageAgent;
    TweetHistory tweetHistory;
    TweetHistoryAgent tweetHistoryAgent;
    ForwardMessage forwardMessage;
    ForwardMessageAgent forwardMessageAgent;
    MessageDetail messageDetail;
    MessageDetailAgent messageDetailAgent;
    TweetCommentsPage tweetCommentsPage;
    TweetCommentsPageAgent tweetCommentsPageAgent;
    WatchAnotherPersonalPage watchAnotherPersonalPage;
    WatchAnotherPersonalPageAgent watchAnotherPersonalPageAgent;
    Chatroom chatroom;
    ChatroomAgent chatroomAgent;
    EditPersonalPage editPersonalInfo;
    EditInfoAgent editInfoAgent;
    UserLists userLists;
    UserListsAgent userListsAgent;
    Announcement announcement;
    AnnouncementAgent announcementAgent;
    TimeLine timeLine;
    TimeLineAgent timeLineAgent;
    ExplorePage explorePage;
    ExplorePageAgent explorePageAgent;
    RandomTweetPage randomTweetPage;
    ChatsPage chatsPage;
    ChatsPageAgent chatsPageAgent;
    Setting setting;
    SettingAgent settingAgent;
    SendMessageAgent sendMessageAgent;
    public CliAgent(LogicalAgent logicalagent) {
        this.father=new Father();
        this.login = new Login();
        this.loginAgent=new LoginAgent();
        this.logicalagent = logicalagent;
        this.menu = new Menu();
        this.menuAgent=new MenuAgent();
        this.personalPage = new PersonalPage();
        this.personalPageAgent=new PersonalPageAgent();
        this.newMessage = new NewMessage();
        this.newMessageAgent=new NewMessageAgent();
        this.tweetHistory = new TweetHistory();
        this.tweetHistoryAgent=new TweetHistoryAgent();
        this.forwardMessage = new ForwardMessage();
        this.forwardMessageAgent=new ForwardMessageAgent();
        this.messageDetail = new MessageDetail();
        this.messageDetailAgent=new MessageDetailAgent();
        this.tweetCommentsPage = new TweetCommentsPage();
        this.tweetCommentsPageAgent=new TweetCommentsPageAgent();
        this.watchAnotherPersonalPage = new WatchAnotherPersonalPage();
        this.watchAnotherPersonalPageAgent=new WatchAnotherPersonalPageAgent();
        this.chatroom = new Chatroom();
        this.chatroomAgent=new ChatroomAgent();
        this.editPersonalInfo = new EditPersonalPage();
        this.editInfoAgent=new EditInfoAgent();
        this.userLists = new UserLists();
        this.userListsAgent=new UserListsAgent();
        this.announcement = new Announcement();
        this.announcementAgent=new AnnouncementAgent();
        this.timeLine=new TimeLine();
        this.timeLineAgent=new TimeLineAgent();
        this.explorePage=new ExplorePage();
        this.explorePageAgent=new ExplorePageAgent();
        this.randomTweetPage=new RandomTweetPage();
        this.chatsPage=new ChatsPage();
        this.chatsPageAgent=new ChatsPageAgent();
        this.setting=new Setting();
        this.settingAgent=new SettingAgent();
        this.sendMessageAgent=new SendMessageAgent();
    }


    public void login() throws IOException {
       loginAgent.login(login,logicalagent);
    }


    public String Menu() {
        return menuAgent.Menu(menu);
    }

    public String PersonalPage() {
        return personalPageAgent.PersonalPage(personalPage);
    }


    public void TimeLine(List<Tweet> tweets, boolean isRandomTweetPage)
            throws IOException {
       timeLineAgent.TimeLine(tweets,isRandomTweetPage,randomTweetPage
       ,timeLine,tweetHistory,logicalagent,forwardMessageAgent,forwardMessage
       ,sendMessageAgent,newMessageAgent,newMessage,tweetCommentsPage,messageDetail
       ,messageDetailAgent,watchAnotherPersonalPageAgent,watchAnotherPersonalPage,
               chatroomAgent,chatroom,tweetCommentsPageAgent);
    }

    public void ExplorePage() throws IOException {
       explorePageAgent.ExplorePage(explorePage,logicalagent
       ,watchAnotherPersonalPageAgent,watchAnotherPersonalPage,chatroomAgent
       ,chatroom,timeLineAgent,randomTweetPage,timeLine,tweetHistory,newMessageAgent,
               newMessage,forwardMessageAgent,forwardMessage
       ,sendMessageAgent,tweetCommentsPage,messageDetail,messageDetailAgent,
               tweetCommentsPageAgent);
    }


    public void Chatroom(User user, Chat chat) throws IOException {
        chatroomAgent.Chatroom(user,chat,chatroom,
                logicalagent,newMessageAgent,newMessage);
    }

    public String Setting() throws IOException {
       return settingAgent.setting(setting,logicalagent,login,menu,
               editPersonalInfo);
      }


      public String logout() throws IOException {
        return loginAgent.logout(logicalagent,menu);
    }


    public void NewTweet() throws IOException {
       newMessageAgent.NewTweet(newMessage,logicalagent);
    }


    public Message NewMessage() throws IOException {
        return newMessageAgent.NewMessage(newMessage,logicalagent);
    }



    public void TweetHistory() throws IOException {
       tweetHistoryAgent.TweetHistory(tweetHistory,logicalagent,
               forwardMessageAgent,forwardMessage,sendMessageAgent,
               newMessageAgent,newMessage,tweetCommentsPageAgent,
               messageDetailAgent,tweetCommentsPage,messageDetail
       ,timeLine,watchAnotherPersonalPageAgent,watchAnotherPersonalPage,
               chatroomAgent,chatroom);
    }




    public void EditPersonalInfo() throws IOException {
       editInfoAgent.EditPersonalInfo(logicalagent
       ,editPersonalInfo,login);
    }

    public void UserLists() throws IOException {
    userListsAgent.UserLists(logicalagent,userLists
    ,watchAnotherPersonalPageAgent,watchAnotherPersonalPage,chatroomAgent
    ,chatroom,newMessageAgent,newMessage);
    }

    public void Announcement() throws IOException {
       announcementAgent.Announcement(logicalagent,announcement);
    }

    public void ChatsPage() throws IOException {
        chatsPageAgent.ChatsPage(chatsPage, logicalagent
                , chatroomAgent,
                chatroom, newMessageAgent, newMessage);
    }
}