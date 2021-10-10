package responses.visitors.auth;

import responses.auth.RegistrationResponse;
import responses.visitors.ResponseVisitor;

public interface RegistrationResponseVisitor extends ResponseVisitor {
       void getAnswer(RegistrationResponse response);
}
