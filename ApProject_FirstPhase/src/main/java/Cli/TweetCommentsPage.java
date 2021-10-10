package Cli;

import Models.Message;

public class TweetCommentsPage extends Father implements CommonActions,
        ShowMessage{
    public void noComment(){
        System.out.println("there is no comment write(0) to Exit");
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
                case "12":
                case "13":
                case "14":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.BLUE_BOLD+
                "In this page you can see Comments, " +
                "also you can do some actions here," +"\n"+
                "after each Comment, " +
                "write the number of" +
                " any action you want to do (nextLine)"+"\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN_BOLD+"Exit" +
                " this page(0), "+
                "goto next Comment(1), goto previous Comment(2)," +
                " Like Comment(3)," +"\n"+
                "DisLike Comment(4)," +
                "forward Comment to My SavedMessage(5)," +
                "forward Comment to others(6), " +
                "\n"+
                "write comment" +
                " for this Comment(7), goto commentPage of this " +
                "Comment(8), CommentDetail(9)," +"\n"+
                " Block User of this Comment(10)" +
                ", Watching Page of this User(11)" +
                ", Report this User(12)"
                +ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD+
                "pay attention, you may see these command " +
                "any moment by writing (13) " +"\n"+
                "or coming " +
                "up and watch these commands again."+
                "\n"+"\n"+ConsoleColors.RESET);
    }
    @Override
    public void showMessage(Message message) {
        if(message.getUser()!=null) {
            System.out.println(ConsoleColors.GREEN + "This Comment" +
                    " was written by " + ConsoleColors.CYAN_BOLD +
                    message.getUser().getAccount().getUsername() +
                    ConsoleColors.RESET);
        }
        else{
            System.out.println(ConsoleColors.GREEN + "This Comment" +
                    " was written by " + ConsoleColors.CYAN_BOLD +
                    "deleted account" +
                    ConsoleColors.RESET);
        }
        System.out.println(ConsoleColors.CYAN +
                message.getText() + ConsoleColors.RESET);
    }
    public void last(){
        System.out.println("last comment");
    }
    public void first(){
        System.out.println("first comment");
    }
}