package resources;

public class Texts {

    public static final String YOU_ARE_ONLINE = "you logged in with another device";
    public static final String DEFAULT_BIO_TEXT = "Hi, I'm using MinabCity";
    public static final String LOGIN_BUTTON_TEXT = "login";
    public static final String REGISTER_BUTTON_TEXT = "register";
    public static final String WELCOME_LABEL_TEXT = "Welcome To Minab City";
    //start panel
    public static final String BACK = "back";
    public static final String CONTINUE = "continue";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String REMOVE_Account = "remove Account";
    public static final String PRIVACY = "privacy";
    public static final String LAST_SEEN = "lastSeen";
    public static final String ACTIVATE = "activate";
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String ALL = "allUsers";
    public static final String NOBODY = "nobody";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String CREATE_ACCOUNT = "create an Account";
    public static final String FIRSTNAME = "firstname**";
    public static final String LASTNAME = "lastname**";
    public static final String USERNAME = "username**";
    public static final String PASSWORD = "password**";
    public static final String EMAIL = "email**";
    public static final String  PHONE = "phone";
    public static final String  BIRTH = "birth";
    public static final String  DELETED_ACCOUNT = "deleted account";
    public static final String  MY_REQUEST  = "myRequest";
    public static final String  OTHER_REQUEST  = "otherRequest";
    public static final String  SYSTEM_MESSAGES  = "systemMessages";
    public static final String REPEATED_EMAIL = "repeated Email";
    public static final String REPEATED_USERNAME = "repeated Username";
    public static final String REPEATED_PASSWORD = "repeated Password";
    public static final String REPEATED_PHONE = "repeated Phone";
    public static final String INVALID_PHONE = "invalid Phone";
    public static final String INVALID_EMAIL = "invalid Email";
     //registration panel
    //login panel
    public static final String WELCOME_BACK = "welcome back!";
    public static final String NO_FRIEND = "no friend";
    public static final String NO_PEOPLE = "no people";
    public static final String INVALID_LOGIN = "invalid username or password";
    public static final String INVALID_SEARCH = "invalid username";
    public static final String NULL = "enter your info";
    public static final String LOGIN = "login";
    //manage biography panel
    public static final String BIOGRAPHY = "write a biography for yourself";
    public static final String PROFILE_IMAGE = "choose a profile image";
    public static final String SELECT_BUTTON = "select";
    public static final String PNG_LABEL = "choose a .png file";
    //
    public static final String TWEET_DEFAULT_TEXT = "What's Happening?";
    public static final String ADD_TWEET_BUTTON_TEXT = "post";
    //
    public static final String ADD_PHOTO_FOR_TWEET = "addPhoto";
    //infoPanel
    public static final String FIRSTNAME_INFO_PANEL = "firstname";
    public static final String LASTNAME_INFO_PANEL = "lastname";
    public static final String USERNAME_INFO_PANEL = "username";
    public static final String PASSWORD_INFO_PANEL = "password";
    public static final String EMAIL_INFO_PANEL = "email";
    public static final String  PHONE_INFO_PANEL = "phone";
    public static final String  BIRTH_INFO_PANEL = "birth";
    public static final String BIOGRAPHY_INFO_PANEL = "biography";
    //change Info panel
    public static final String SAVE_BUTTON = "save";

    //menuBar
    public static final String MENUBAR = "MORE";
    public static final String LIKE = "likes: ";
    public static final String DISLIKE = "dislikes: ";


    //tweetHistoryPanel
    public static final String NO_TWEET = "there is no tweet ";
    public static final String NO_COMMENT = "there is no comment ";

    public static final String FOLLOW = "follow";
    public static final String REQUEST = "request";
    public static final String UNFOLLOW = "unfollow";
    public static final String FOLLOWINGS = "followings: ";
    public static final String FOLLOWINGS1 = "followings ";
    public static final String FOLLOWERS = "followers: ";
    public static final String BLACK_LIST = "blackList: ";
    public static final String MY_LIST = "myList: ";
    public static final String NEW_GROUP = "newGroup";
    public static final String NO_CHAT = "there is no messages";
    public static final String SAVED_MESSAGES = "saved messages";
    public static final String  MESSAGE = "write your message";
    public static final String  DELETE = "delete";
    public static final String  EDIT = "edit";
    public static final String  DELETED_MESSAGE = "Message deleted";
    public static final String  GROUP_NAME = "groupName";
    public static final String  LIST_NAME = "listName";


    public static String TextHandling(String s,String fontType){

        String html1 = "<html><body " +
                "style='font-family: Serif; font-style: italic; " +
                "font-size: 16px; padding: 0px; margin: 0px;" +
                " width: ";
        String html2 = "px'>";
        String html3 = "</body></html>";
        return (html1+"200"+html2+s+html3);
    }

    //commentPanel
    public static final String COMMENT_J_TEXTAREA_DEFAULT_TEXT =
            "Add another Tweet";

    public static final String COMMENT_J_TEXTAREA_DEFAULT_TEXT_2 =
            "Add your comment";

    public static final String SEARCH_AREA_DEFAULT_TEXT =
            "search username";
}
