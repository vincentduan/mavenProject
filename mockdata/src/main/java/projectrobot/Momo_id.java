package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;

public class Momo_id extends Account {
    @JSONField(name="username")
    private String username;
    public Momo_id(String value) {
        super(value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
