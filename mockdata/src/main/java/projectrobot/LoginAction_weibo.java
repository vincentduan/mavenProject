package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginAction_weibo extends LoginAction {

    @JSONField(name="about")
    private Weibo_id about;
    @JSONField(name="by")
    private PhoneNumber by;
    @JSONField(name="dgraph.type")
    private String type;

    public LoginAction_weibo() {
        this.type = "LoginAction_weibo";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Weibo_id getAbout() {
        return about;
    }

    public void setAbout(Weibo_id about) {
        this.about = about;
    }

    public PhoneNumber getBy() {
        return by;
    }

    public void setBy(PhoneNumber by) {
        this.by = by;
    }

    public static LoginAction generateLoginAction_weibo() {
        Weibo_id weibo_id = Weibo_id.generateWeiboId();
        LoginAction_weibo loginAction_weibo = new LoginAction_weibo();
        loginAction_weibo.setTime(Utils.getRandomTime());
        loginAction_weibo.setAbout(weibo_id);
        loginAction_weibo.setBy(PhoneNumber.generatePhoneNumber());
        return loginAction_weibo;
    }

}
