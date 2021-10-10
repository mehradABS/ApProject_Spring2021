package events.visitors.setting;

import events.setting.SettingEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface SettingPanelEventVisitor extends EventVisitor {

    Response getEvent(SettingEvent event);
}
