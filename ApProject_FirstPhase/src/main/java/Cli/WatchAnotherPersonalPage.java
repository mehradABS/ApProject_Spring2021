package Cli;

import Models.Account;


public class WatchAnotherPersonalPage extends Father {


    public String  GetCommand(boolean four) {
        if (four) {
            while (true) {
                String a = scanner.next();
                switch (a) {
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                        return a;
                    default:
                        this.invalidCommand();
                        break;
                }
            }
        }
        else{
            while (true) {
                String a = scanner.next();
                switch (a) {
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                        return a;
                    default:
                        this.invalidCommand();
                        break;
                }
            }
        }
    }

    public void showingAccount(Account account,String lastSeen) {
        System.out.println(ConsoleColors.CYAN_BOLD+
                account.getUsername()+
                "   "+account.getUser().getFirstname()+
                account.getUser().getLastname()+"   "+
                lastSeen+ConsoleColors.RESET);
    }
    public void follow(Account account){
        System.out.println(ConsoleColors.CYAN+
                "you Followed "+account.getUsername()+ConsoleColors.RESET);
    }
    public void followAsk(Account account){
        System.out.print(ConsoleColors.CYAN);
        System.out.println("do you want follow "
        +account.getUsername()+"?(1)");
        System.out.print(ConsoleColors.RESET);
    }
    public void followRequest(){
        System.out.println(ConsoleColors.CYAN+
                "Ask for Following(1)");
    }
    public void unfollowAsk(Account account){
        System.out.print(ConsoleColors.CYAN);
        System.out.println("do you want unfollow "
                +account.getUsername()+"?(1)");
        System.out.print(ConsoleColors.RESET);
    }
    public void report(Account account){
        System.out.print(ConsoleColors.CYAN);
        System.out.println("do you want report "+
                account.getUsername()+"?(2)");
        System.out.print(ConsoleColors.RESET);
    }
    public void Block(Account account){
        System.out.print(ConsoleColors.CYAN);
        System.out.println("do you want Block "+
                account.getUsername()+"?(3)");
        System.out.print(ConsoleColors.RESET);
    }
    public void unBlock(Account account){
        System.out.print(ConsoleColors.CYAN);
        System.out.println("do you want unBlock "+
                account.getUsername()+"?(3)");
        System.out.print(ConsoleColors.RESET);
    }
    public void Exit(){
        System.out.println(ConsoleColors.CYAN+"Exit this page(0)"+
                ConsoleColors.RESET);
    }
    public void sendMessage(){
        System.out.print(ConsoleColors.CYAN);
        System.out.print("Do you want send a message?(5)");
        System.out.print(ConsoleColors.RESET);
    }

    public void mute(){
        System.out.println(ConsoleColors.BLUE+"mute this user(4)"
        +ConsoleColors.RESET);
    }
    public void unMute(){
        System.out.println(ConsoleColors.BLUE+
                "unMute this user(4)"+ConsoleColors.RESET);
    }
}