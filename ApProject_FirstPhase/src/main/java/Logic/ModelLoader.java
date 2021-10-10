package Logic;

import Models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class ModelLoader {
    private final GsonBuilder gsonBuilder= new GsonBuilder();

    private final Gson gson=gsonBuilder.setPrettyPrinting().create();

    public Program loadProgram() throws IOException {
        Program program=new Program();
        File file=new File("src\\Save\\Users");
        for (File UserFile : Objects.requireNonNull(file.listFiles())) {
            FileReader fileReader=new FileReader(UserFile);
            User user=gson.fromJson(fileReader,User.class);
            program.originalUsers().add(user);
            fileReader.close();
        }
        File file1=new File("src\\Save\\requests");
        for (File reqFiles:Objects.requireNonNull(file1.listFiles())) {
            FileReader fileReader=new FileReader(reqFiles);
            Request request=gson.fromJson(fileReader,Request.class);
            program.getAllRequest().add(request);
            fileReader.close();
        }
        File file2=new File("src\\Save\\Tweets");
        for (File TweetFile: Objects.requireNonNull(file2.listFiles())) {
            FileReader fileReader=new FileReader(TweetFile);
            Tweet tweet=gson.fromJson(
                    fileReader,Tweet.class);
            program.getAllTweets().add(tweet);
            fileReader.close();
        }
        File file3=new File("src\\Save\\messages");
        for (File msFile: Objects.requireNonNull(file3.listFiles())) {
            FileReader fileReader=new FileReader(msFile);
            Message message=gson.fromJson(fileReader,
                    Message.class);
            program.getAllMessages().add(message);
            fileReader.close();
        }
        File file4=new File("src\\Save\\comments");
        for (File cmFile: Objects.requireNonNull(file4.listFiles())) {
            FileReader fileReader=new FileReader(cmFile);
            Comment comment=gson.fromJson(fileReader,
                    Comment.class);
            program.getAllComments().add(comment);
            fileReader.close();
        }
        File file5=new File("src\\Save\\Chats");
        for (File chFile: Objects.requireNonNull(file5.listFiles())) {
            FileReader fileReader=new FileReader(chFile);
            Chat chat=gson.fromJson(fileReader,
                    Chat.class);
            program.getAllChats().add(chat);
            fileReader.close();
        }
        File file6=new File("src\\Save\\reports");
        Type type=new TypeToken<HashMap<String,String>>(){}.getClass();
        for (File rpFile: Objects.requireNonNull(file6.listFiles())) {
            FileReader fileReader=new FileReader(rpFile);
            HashMap <String,String> rp=gson.fromJson(fileReader,type);
            program.setReportsId(rp);
            fileReader.close();
        }

        File file7=new File("src\\Save\\Id\\user.json");
        if(file7.exists()) {
            FileReader fileReader=new FileReader(file7);
            User.setId_counter(gson.fromJson(fileReader, Integer.class));
            fileReader.close();
        }
        File file8=new File("src\\Save\\Id\\chat.json");
        if(file8.exists()) {
            FileReader fileReader=new FileReader(file8);
            Chat.setId_counter(gson.fromJson(fileReader, Integer.class));
            fileReader.close();
        }
        File file9=new File("src\\Save\\Id\\request.json");
        if(file9.exists()) {
            FileReader fileReader=new FileReader(file9);
            Request.setId_counter(gson.fromJson(fileReader, Integer.class));
            fileReader.close();
        }
        File file10=new File("src\\Save\\Id\\message.json");
        if(file10.exists()) {
            FileReader fileReader=new FileReader(file10);
            Message.setId_counter(gson.fromJson(fileReader, Integer.class));
            fileReader.close();
        }
        for (User user:program.originalUsers()) {
            user.setBlacklist(new LinkedList<>());
        }
        for (User user: program.originalUsers()) {
            for (Integer username:user.getBlackUsername()) {
                for (User badUser: program.originalUsers()) {
                    if(username==badUser.getId()){
                        user.getBlacklist().add(badUser);
                        break;
                    }
                }
            }
        }
        for (User user:program.originalUsers()) {
            user.setFollowers(new LinkedList<>());
        }
        for (User user: program.originalUsers()) {
            for (Integer username:user.getFollowerUsername()) {
                for (User follower: program.originalUsers()) {
                    if(username==follower.getId()){
                        user.getFollowers().add(follower);
                        break;
                    }
                }
            }
        }
        for (User user: program.originalUsers()) {
            user.setFollowings(new LinkedList<>());
        }
        for (User user: program.originalUsers()) {
            for (Integer username:user.getFollowingUsername()) {
                for (User following: program.originalUsers()) {
                    if(username== following.getId()){
                        user.getFollowings().add(following);
                        break;
                    }
                }
            }
        }
        for (User user:program.originalUsers()) {
            for (User.ListName listName:user.getFollowingsList()) {
                listName.setList(new LinkedList<>());
            }
        }
        for (User user: program.originalUsers()) {
            for (User.ListName listName: user.getFollowingsList()) {
                for (Integer username: listName.getUsernames()) {
                    for (User following:user.getFollowings()) {
                        if(username == following.getId()){
                            listName.getList().add(following);
                            break;
                        }
                    }
                }
            }
        }
        for (User user: program.originalUsers()) {
            user.setMyRequests(new LinkedList<>());
            user.setRequestsFromMe(new LinkedList<>());
        }
        for (User user: program.originalUsers()) {
            for (Integer MyReqId:user.getMyReqId()) {
                for (Request req: program.getAllRequest()) {
                    if(req.getId() == MyReqId){
                        req.setSourceUser(user);
                        user.getMyRequests().add(req);
                        break;
                    }
                }
            }
        }
        for (User user: program.originalUsers()) {
            for (Integer ReqFromMeId:user.getReqFromMeId()) {
                for (Request req: program.getAllRequest()) {
                    if(req.getId()== ReqFromMeId){
                        user.getRequestsFromMe().add(req);
                        break;
                    }
                }
            }
        }
        for (Tweet tweet: program.getAllTweets()) {
            tweet.setComments(new LinkedList<>());
            tweet.setUsersLikeThis(new HashSet<>());
            tweet.setUsersDisLikeThis(new HashSet<>());
        }
        for (Tweet tweet: program.getAllTweets()) {
            for (Integer id:tweet.getCommentsId()) {
                for (Comment comment:program.getAllComments()) {
                    if(id==comment.getId()){
                        tweet.getComments().add(comment);
                        break;
                    }
                }
            }
        }
        for (Comment comment:program.getAllComments()) {
            comment.setComments(new LinkedList<>());
            comment.setUsersDisLikeThis(new HashSet<>());
            comment.setUsersLikeThis(new HashSet<>());
        }
        for (Comment originalCm: program.getAllComments()) {
            for (Integer id:originalCm.getCommentsId()) {
                for (Comment comment:program.getAllComments()) {
                    if(id==comment.getId()){
                        originalCm.getComments().add(comment);
                        break;
                    }
                }
            }
        }

        for (Tweet tweet: program.getAllTweets()) {
            for (Integer likeId: tweet.getLike()) {
                for (User user: program.originalUsers()) {
                    if(likeId==user.getId()){
                        tweet.getUsersLikeThis().add(user);
                        break;
                    }
                }
            }
        }

        for (Tweet tweet: program.getAllTweets()) {
            for (Integer disLikeId: tweet.getDislike()) {
                for (User user: program.originalUsers()) {
                    if(disLikeId==user.getId()){
                        tweet.getUsersDisLikeThis().add(user);
                        break;
                    }
                }
            }
        }

        for (Comment comment: program.getAllComments()) {
            for (Integer likeId: comment.getLike()) {
                for (User user: program.originalUsers()) {
                    if(likeId==user.getId()){
                        comment.getUsersLikeThis().add(user);
                        break;
                    }
                }
            }
        }


        for (Comment comment: program.getAllComments()) {
            for (Integer disLikeId: comment.getDislike()) {
                for (User user: program.originalUsers()) {
                    if(disLikeId==user.getId()){
                        comment.getUsersDisLikeThis().add(user);
                        break;
                    }
                }
            }
        }

        for (Message message: program.getAllMessages()) {
            message.setUser(null);
            for (User user: program.originalUsers()) {
                if(user.getId()== message.getUserId()){
                    message.setUser(user);
                    break;
                }
            }
        }

        for (Comment comment: program.getAllComments()) {
            comment.setUser(null);
            for (User user: program.originalUsers()) {
                if(user.getId()== comment.getUserId()){
                    comment.setUser(user);
                    break;
                }
            }
        }
        for (Tweet tweet: program.getAllTweets()) {
            tweet.setUser(null);
            for (User user: program.originalUsers()) {
                if(user.getId()== tweet.getUserId()){
                    tweet.setUser(user);
                    break;
                }
            }
        }
        for (User user:program.originalUsers()) {
            user.setMyTweets(new LinkedList<>());
            user.setFavouriteTweets(new HashSet<>());
            user.setMyChats(new HashMap<>());
            user.setUnreadChats(new HashMap<>());
            user.setSavedMessage(new LinkedList<>());
            user.setSilentUsers(new LinkedList<>());
        }
        for (User user: program.originalUsers()) {
            for (Integer id:user.getTweetsID()) {
                for (Tweet tweet: program.getAllTweets()) {
                    if(tweet.getId()==id){
                        user.getMyTweets().add(tweet);
                        break;
                    }
                }
            }
        }
        for (User user: program.originalUsers()) {
            for (Integer id:user.getFavouriteID()) {
                for (Tweet tweet: program.getAllTweets()) {
                    if(tweet.getId()==id){
                        user.getFavouriteTweets().add(tweet);
                        break;
                    }
                }
            }
        }
        for (User user:program.originalUsers()) {
            for (Integer id:user.getSavedMessageId()) {
                for (Tweet tweet: program.getAllTweets()) {
                    if(id==tweet.getId()){
                        user.getSavedMessage().add(tweet);
                        break;
                    }
                }
            }
        }
        for (User user:program.originalUsers()) {
            for (Integer id:user.getSavedMessageId()) {
                for (Comment comment: program.getAllComments()) {
                    if(id==comment.getId()){
                        user.getSavedMessage().add(comment);
                        break;
                    }
                }
            }
        }
        for (User user:program.originalUsers()) {
            for (Integer id:user.getSavedMessageId()) {
                for (Message message: program.getAllMessages()) {
                    if(id==message.getId()){
                        user.getSavedMessage().add(message);
                        break;
                    }
                }
            }
        }
        for (User user: program.originalUsers()) {
            for (Integer silentNames:user.getSilentUsername()) {
                for (User silentUser:program.originalUsers()) {
                    if(silentUser.getId()==silentNames){
                        user.getSilentUsers().add(silentUser);
                        break;
                    }
                }
            }
        }
        for (Chat chat: program.getAllChats()) {
            chat.setMembers(new LinkedList<>());
            chat.setMessages(new LinkedList<>());
            chat.setForwardMessages(new LinkedList<>());
        }


        for (Chat chat: program.getAllChats()) {
            for (Integer id: chat.getId_Message()) {
                for (Message message: program.getAllMessages()) {
                    if(message.getId()==id){
                        chat.getMessages().add(message);
                        break;
                    }
                }
            }
            for (Integer id: chat.getId_forwardMessage()) {
                for (Message message: program.getAllMessages()) {
                    if(message.getId()==id){
                        chat.getForwardMessages().add(message);
                        break;
                    }
                }
            }
            for (Integer id: chat.getId_Message()) {
                for (Comment comment: program.getAllComments()) {
                    if(comment.getId()==id){
                        chat.getMessages().add(comment);
                        break;
                    }
                }
            }
            for (Integer id: chat.getId_forwardMessage()) {
                for (Comment comment: program.getAllComments()) {
                    if(comment.getId()==id){
                        chat.getForwardMessages().add(comment);
                        break;
                    }
                }
            }
            for (Integer id: chat.getId_Message()) {
                for (Tweet tweet: program.getAllTweets()) {
                    if(tweet.getId()==id){
                        chat.getMessages().add(tweet);
                        break;
                    }
                }
            }
            Chat.sortMessages(chat.getMessages());
            for (Integer id: chat.getId_forwardMessage()) {
                for (Tweet tweet: program.getAllTweets()) {
                    if(tweet.getId()==id){
                        chat.getForwardMessages().add(tweet);
                        break;
                    }
                }
            }
        }

        for (User user: program.originalUsers()) {
            for (Chat chat: program.getAllChats()) {
                if(user.getChatID().contains(chat.getId())){
                    chat.getMembers().add(user.getAccount().getUsername());
                    user.getMyChats().put(chat,new LinkedList<>());
                    user.getUnreadChats().put(chat,new LinkedList<>());
                }
            }
        }
        for (User user:program.originalUsers()) {
            for (Message message:program.getAllMessages()) {
                if(user.getUnreadMessage().contains(message.getId())){
                    for (Chat chat:user.getMyChats().keySet()) {
                        if(chat.getMessages().contains(message)){
                            user.getUnreadChats().get(chat).add(message);
                            break;
                        }
                    }
                }
            }
            for (Comment comment:program.getAllComments()) {
                if(user.getUnreadMessage().contains(comment.getId())){
                    for (Chat chat:user.getMyChats().keySet()) {
                        if(chat.getMessages().contains(comment)){
                            user.getUnreadChats().get(chat).add(comment);
                            break;
                        }
                    }
                }
            }
            for (Tweet tweet:program.getAllTweets()) {
                if(user.getUnreadMessage().contains(tweet.getId())){
                    for (Chat chat:user.getMyChats().keySet()) {
                        if(chat.getMessages().contains(tweet)){
                            user.getUnreadChats().get(chat).add(tweet);
                            break;
                        }
                    }
                }
            }
        }

        for (User user:program.originalUsers()) {
            user.getAccount().setUser(user);
        }
        for (User user: program.originalUsers()) {
            program.getAllEmails().add(user.getEmailAddress());
            program.getAllPhoneNumbers().add(user.getPhoneNumber());
            program.getAllInfo().put(user.getAccount().getUsername(),
                    user.getAccount().getPassword());
        }
        for (Chat chat: program.getAllChats()) {
            if(chat.getMembers().size()==1){
                chat.getMembers().add("deleted account");
            }
        }
        return program;
    }

    public void setUserChanges(User user) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Users\\"+user.
                getId()+".json",false);
        gson.toJson(user,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setTweetChanges(Tweet tweet) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Tweets\\"+tweet.getId()
        +".json",false);
        gson.toJson(tweet,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setMessageChanges(Message message) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\messages\\"+message.getId()
                +".json",false);
        gson.toJson(message,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setCommentChanges(Comment message) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\comments\\"+message.getId()
                +".json",false);
        gson.toJson(message,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setChatChanges(Chat chat) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Chats\\"+chat.getId()
                +".json",false);
        gson.toJson(chat,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setReports(HashMap<String,String> reports) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\reports\\rp.json",
                true);
        gson.toJson(reports,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setRequestChanges(Request request) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\requests\\" +
                request.getId()+".json",
                false);
        gson.toJson(request,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setUserID_counter(int id) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Id\\user.json",
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setMessageID_counter(int id) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Id\\message.json",
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setChatID_counter(int id) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Id\\chat.json",
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setRequestID_counter(int id) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Save\\Id\\request.json",
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void delete(File file){
        file.delete();
    }
}