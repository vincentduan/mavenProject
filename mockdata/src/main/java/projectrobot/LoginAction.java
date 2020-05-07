package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginAction {
    @JSONField(name="time")
    private String time;

    public LoginAction() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LoginAction(String time) {
        this.time = time;
    }
}
