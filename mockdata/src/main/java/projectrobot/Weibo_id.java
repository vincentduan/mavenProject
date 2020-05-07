package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class Weibo_id extends Account {

    @JSONField(name="username")
    private String username;
    @JSONField(name="act")
    private List<WeiboAction> act;
    @JSONField(name="about")
    private List<WeiboAction> about;
    @JSONField(name="weibo_id_login_by")
    private List<WeiboAction> weibo_id_login_by;

    public Weibo_id(String datasource) {
        super(datasource);
    }

    public Weibo_id() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WeiboAction> getAct() {
        return act;
    }

    public void setAct(List<WeiboAction> act) {
        this.act = act;
    }

    public List<WeiboAction> getAbout() {
        return about;
    }

    public void setAbout(List<WeiboAction> about) {
        this.about = about;
    }

    public List<WeiboAction> getWeibo_id_login_by() {
        return weibo_id_login_by;
    }

    public void setWeibo_id_login_by(List<WeiboAction> weibo_id_login_by) {
        this.weibo_id_login_by = weibo_id_login_by;
    }

    public static Weibo_id generateWeiboId() {
        Faker faker = new Faker(Locale.CHINA);
        String username = faker.name().username();
        Weibo_id weibo_id1 = new Weibo_id();
        weibo_id1.setUsername(username);
        return weibo_id1;
    }
}
