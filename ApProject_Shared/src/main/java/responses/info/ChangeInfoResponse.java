package responses.info;

import responses.auth.RegistrationResponse;

public class ChangeInfoResponse extends RegistrationResponse {

    @Override
    public String getVisitorType() {
        return "ChangeInfoResponseVisitor";
    }
}
