package Cli;

import Models.Message;
import Models.Tweet;

public class TweetHistory extends Father
        implements CommonActions,ShowMessage {

    public void noTweet(){
        System.out.println("there is no tweet write (0) to Exit");
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.BLUE_BOLD+
                "In this page you can see your Tweets," +
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
                " for Tweet(7), goto commentPage of this " +
                "Tweet(8), TweetDetail(9)"
                +ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD+
                "pay attention, you may see these command " +
                "any moment by writing (10) " +"\n"+
                "or coming " +
                "up and watch these commands again."+
                "\n"+"\n"+ConsoleColors.RESET);
    }
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
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }
    @Override
    public void showMessage(Message message) {
        System.out.println(ConsoleColors.CYAN_BOLD+
                message.getUser().getAccount().getUsername()+": "+
                ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN+
                message.getText()+ConsoleColors.RESET);
    }

    public void last(){
        System.out.println("last tweet");
    }
    public void first(){
        System.out.println("first tweet");
    }
}