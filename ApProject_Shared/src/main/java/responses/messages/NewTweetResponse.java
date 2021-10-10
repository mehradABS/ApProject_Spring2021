package responses.messages;

import responses.auth.ReceivingImageConfirmationResponse;

public class NewTweetResponse extends ReceivingImageConfirmationResponse {

    @Override
    public String getVisitorType() {
        return "confirmationNewTweetResponseVisitor";
    }
}
