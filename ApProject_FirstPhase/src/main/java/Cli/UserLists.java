package Cli;

import Models.User;


import java.util.LinkedList;
import java.util.List;

public class UserLists extends Father implements CommonActions{

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
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.RED+
                "FollowersList(1)   FollowingsList(2)   " +
                "BlackList(3)   CategorizeFollowing(4)");
        System.out.println(ConsoleColors.CYAN+
                "Write the number of the list which you want to watch");
        System.out.println("(Exit this Page(0))"+ConsoleColors.RESET);
    }
    public void showList(List<User> open,String name){
        List<User> list = new LinkedList<>();
        for (User user:open) {
            if(user.getAccount().isActive()){
                list.add(user);
            }
        }
        System.out.println(ConsoleColors.CYAN+
                "You can watch personalPage of these users" +
                " by writing their username(Exit this list(0))");
        if(name.equals("BlackList")){
            System.out.println("Also you can unblock " +
                    "these user by writing: " +
                    "username//  example mehrad//");
        }
        if(name.equals("listName")){
            System.out.println("Also you can add or remove users from this" +
                    " list like this:\nusername[](add)  username][(remove)" +
                    "  example mehrad][");
        }
        if(list.size()==0){
            System.out.println("there is no user");
        }
        int i=0;
        while(i<list.size()){
            int b=list.size()-i;
            System.out.print(list.get(i).getAccount().getUsername());
            if(b>3){
                System.out.print("   "+list.get(i+1).getAccount().getUsername());
                System.out.println("   "+list.get(i+2).getAccount().getUsername());
            }
            else{
                for (int j =1 ; j <= b-1; j++) {
                    System.out.print("   "+
                            list.get(i+j).getAccount().getUsername());
                }
            }
            i+=3;
        }
        System.out.print(ConsoleColors.RESET);
    }

    public String getUsername(){
        return this.scanner.next();
    }

    public void invalid(){
        System.out.println("invalid username");
    }
    public void categorize(User user){
        System.out.print(ConsoleColors.GREEN);
        System.out.println("Lists: ");
        if(user.getFollowingsList().size()==0){
            System.out.println("there is no list");
        }
        for (User.ListName listName:user.getFollowingsList()) {
            System.out.println(listName.getName());
        }
        System.out.println(ConsoleColors.CYAN+
                "write the name of the list which you want to see its users\n(" +
                "it can be new list(Exit(0)))"
        +ConsoleColors.RESET);
    }
    public void before(){
        System.out.println("you added him/her before");
    }
}