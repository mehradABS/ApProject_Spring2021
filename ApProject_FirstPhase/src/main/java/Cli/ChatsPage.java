package Cli;



public class ChatsPage extends Father implements CommonActions{
    @Override
    public String GetCommand() {
        return this.scanner.next();
    }
    public void chatHistory(){
        System.out.println("chatHistory: ");
    }
    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN+
                "write username of whom you want to chat(write myself" +
                " for saved Messages)");
        System.out.println("Exit this page(0)" +
                ", if you want start a new message, write the username too.");
        System.out.println(ConsoleColors.RESET);
    }
    public void showUsernames(int unreadMessages,String Username) {
        System.out.print(ConsoleColors.GREEN+ Username);
        if (unreadMessages > 0) {
            System.out.print("(" + unreadMessages + ")");
        }
        System.out.println(ConsoleColors.RESET);
    }

    public void invalid(){
        System.out.println("invalid name(he/she is not in your Followings or " +
                "in your followers or his/her account is close)");
    }

    public void BlockUser(){
        System.out.println("you blocked this user");
    }

    public void BlockUser1(String name){
        System.out.println(name+"blocked you");
    }
}