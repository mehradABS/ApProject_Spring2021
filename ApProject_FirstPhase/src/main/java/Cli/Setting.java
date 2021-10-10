package Cli;

import Models.Account;

public class Setting extends Father implements CommonActions {
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
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN +
                "change your account privacy(1)" +
                ", change your LastSeenSetting(2)" +
                "change your password(3)" + "\n" +
                "open or close your account(4), remove your account(5),");
        System.out.println(ConsoleColors.GREEN +
                "write the number of the action which you want to do" +
                "(Exit this Page(0))");
        System.out.print(ConsoleColors.RESET);
    }

    public void changePrivacy(Account account) {
        System.out.println(ConsoleColors.GREEN + "two state " +
                "public -- private");
        System.out.println(
                "your privacy is " + account.getPrivacy() +
                        " do you want to change it(write 1)(Exit (0))");
        System.out.print(ConsoleColors.RESET);
    }

    public boolean changeAccountActivate(Account account) {
        if (account.isActive()) {
            System.out.println(ConsoleColors.CYAN +
                    "your account is open" +
                    " do you want to close it(write 1)(Exit (0))");
            System.out.print(ConsoleColors.RESET);
            return true;
        }
        else {
            System.out.println(ConsoleColors.CYAN +
                    "your account is close" +
                    " do you want to open it(write 1)(Exit (0))");
            System.out.print(ConsoleColors.RESET);
            return false;
        }
    }

    public void changeLastSeen(Account account){
        System.out.println("your state: "+account.getWhoCanSeeLastSeen());
        System.out.println(ConsoleColors.CYAN+
                "we have three state 1-allUsers 2-nobody 3-just yourFollowing" +
                "\nwrite the number of state which you want or(0)to Exit.");
    }

    public void deleteAccount(boolean f){
        System.out.println("your account deleted successfully");
    }
}