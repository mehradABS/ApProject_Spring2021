package Cli.agent;

import Cli.PersonalPage;

public class PersonalPageAgent {
    public String PersonalPage(PersonalPage personalPage) {
        personalPage.showingOptions();
        return personalPage.GetCommand();
    }
}
