package responses.visitors;

import responses.BotResponse;

public interface BotResponseVisitor extends ResponseVisitor{

    void getAnswer(BotResponse response);
}
