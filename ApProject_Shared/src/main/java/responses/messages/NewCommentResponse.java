package responses.messages;

import responses.auth.ReceivingImageConfirmationResponse;

public class NewCommentResponse extends ReceivingImageConfirmationResponse {

    private String responseVisitorType;

    @Override
    public String getVisitorType() {
        return responseVisitorType;
    }

    public void setResponseVisitorType(String responseVisitorType) {
        this.responseVisitorType = responseVisitorType;
    }
}
