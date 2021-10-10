package responses;

import models.auth.OUser;
import responses.visitors.BotResponseVisitor;
import responses.visitors.ResponseVisitor;

public class BotResponse extends Response {

    private String answer;
    private OUser bot;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((BotResponseVisitor)responseVisitor).getAnswer(this);
    }

    @Override
    public String getVisitorType() {
        return "BotResponseVisitor";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public OUser getBot() {
        return bot;
    }

    public void setBot(OUser bot) {
        this.bot = bot;
    }
}
