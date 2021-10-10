package responses.messages;

import responses.auth.ReceivingImageConfirmationResponse;

public class NewMessageResponse extends ReceivingImageConfirmationResponse {

    @Override
    public String getVisitorType() {
        return "confirmationNewMessageResponseVisitor";
    }
}
