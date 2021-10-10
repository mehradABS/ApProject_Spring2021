package Cli;

public class ExplorePage extends Father implements CommonActions{

    @Override
    public String GetCommand() {
        while(true){
            String a= scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN_BOLD+
                "Watching the account of others(1)" +
                ", Watching RandomTweetsPage(2)");
        System.out.println(ConsoleColors.CYAN+
                "write the number of the action which you want to do" +
                "(Exit this Page(0))"+ConsoleColors.RESET);
    }
    public void guideUsername(){
        System.out.println(ConsoleColors.GREEN+
                "write username of account which you want to see" +
                "(back to options by writing(0))"
        +ConsoleColors.RESET);
    }
    public String GetUsername(){
        return scanner.next();
    }

    public void invalidUse(){
        System.out.println("invalid username");
    }
}