package events.visitors.watchProfile;

import events.visitors.EventVisitor;
import responses.Response;

public interface WatchProfileEventVisitor extends EventVisitor {

    Response loadInfo(int userId);
    Response unfollow(int userId);
    Response follow(int userId);
    Response request(int userId);
    Response report(int userId);
    Response block(int userId);
    Response unblock(int userId);
    Response mute(int userId);
    Response unMute(int userId);

}
