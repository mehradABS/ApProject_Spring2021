package Cli;

public class PersonalPage extends Father implements CommonActions{

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.RED_BOLD
                +"Exit this page(0)"+"\n"+"You can put new Tweet(1)");
        System.out.println("You can watch your TweetHistory(2)");
        System.out.println("You can watch your personal info or edit them(3)");
        System.out.println("You can watch your  " +
                "Followers, Followings,and your Black List(4)");
        System.out.println("You can watch your Follow requests and " +
                "your new followers(5)"+ConsoleColors.RESET);
    }

    @Override
    public String GetCommand() {
        System.out.println(ConsoleColors.CYAN+"write the number " +
                "of the action which you want to do"
                +ConsoleColors.RESET);
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
}