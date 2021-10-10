package responses.visitors.setting;

import responses.setting.SettingResponse;
import responses.visitors.ResponseVisitor;

public interface SettingPanelResponseVisitor extends ResponseVisitor {

    void getResponse(SettingResponse response);
}
