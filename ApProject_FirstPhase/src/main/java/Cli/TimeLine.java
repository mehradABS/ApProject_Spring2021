package Cli;

import Models.Message;

public class TimeLine extends Father implements CommonActions,
    ShowMessage{

    @Override
    public String GetCommand() {
        while(true){
            String a= scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    public void Introduction(){
        System.out.println(ConsoleColors.CYAN+
                "in this Page you can see your FollowingsTweets" +
                " and FavouriteTweets"
        +ConsoleColors.RESET);
    }
    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.BLUE_BOLD+
                "also you can do some actions here," +"\n"+
                "after each Tweet, " +
                "write the number of" +
                " any action you want to do (nextLine)"+"\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN_BOLD+"Exit" +
                " this page(0), "+
                "goto next Tweet(1), goto previous Tweet(2)," +
                " Like Tweet(3)," +"\n"+
                "DisLike Tweet(4)," +
                "forward Tweet to My SavedMessage(5)," +
                "forward Tweet to others(6), " +
                "\n"+
                "write comment" +
                " for this Tweet(7), goto commentPage of this " +
                "Tweet(8), TweetDetail(9)," +"\n"+
                " Block User of this Tweet(10)" +
                ", Watching Page of this User(11)" +
                ", Report this User(12)"+"\n"+
                "reTweet this Tweet(13), silent the User(14)"
                +ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD+
                "pay attention, you may see these command " +
                "any moment by writing (15) " +"\n"+
                "or coming " +
                "up and watch these commands again."+
                "\n"+"\n"+ConsoleColors.RESET);
    }

    @Override
    public void showMessage(Message message) {
        System.out.println(ConsoleColors.GREEN+"This Tweet" +
                " was written by "+ConsoleColors.CYAN_BOLD+
                message.getUser().getAccount().getUsername()+
                ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN+
                message.getText()+ConsoleColors.RESET);
    }

    public void noTweet(){
        System.out.println("there is no tweet write 0 to Exit");
    }

    public void Block(){
        System.out.println("you blocked him/her before");
    }

    public void crazy(){
        System.out.println("are you crazy?");
    }
    public void delete(){
        System.out.println("he/she deleted his/her account");
    }
    public void active(){
        System.out.println("his/her account is not active");
    }
}