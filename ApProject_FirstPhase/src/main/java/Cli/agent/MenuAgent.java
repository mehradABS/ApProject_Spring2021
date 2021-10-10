package Cli.agent;

import Cli.Menu;

public class MenuAgent {
    public String Menu(Menu menu) {
        menu.showingOptions();
        return menu.GetCommand();
    }
}
