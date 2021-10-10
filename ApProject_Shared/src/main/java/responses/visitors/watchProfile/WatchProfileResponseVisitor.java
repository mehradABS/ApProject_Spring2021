package responses.visitors.watchProfile;

import responses.visitors.ResponseVisitor;
import responses.watchProfile.WatchProfileResponse;
import responses.watchProfile.LoadInfoResponse;

public interface WatchProfileResponseVisitor extends ResponseVisitor {

      void getAnswer(WatchProfileResponse response);
      void setInfo(LoadInfoResponse loadInfoResponse);
}
