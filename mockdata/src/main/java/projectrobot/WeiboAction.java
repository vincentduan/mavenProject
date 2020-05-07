package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.util.Locale;

public class WeiboAction {
    @JSONField(name="action_type")
    private String action_type;
    @JSONField(name="time")
    private String time;
    @JSONField(name="comment")
    private String comment;
    @JSONField(name="from")
    private Weibo_id from;
    @JSONField(name="to")
    private Weibo_id to;

    @Override
    public String toString() {
        return "WeiboAction{" +
                "action_type='" + action_type + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Weibo_id getFrom() {
        return from;
    }

    public void setFrom(Weibo_id from) {
        this.from = from;
    }

    public Weibo_id getTo() {
        return to;
    }

    public void setTo(Weibo_id to) {
        this.to = to;
    }

    public static WeiboAction generateWeiboAction() {
        Faker faker = new Faker(Locale.CHINA);
        String action_type = Utils.action_type[faker.number().numberBetween(0, Utils.action_type.length)];
        String time = Utils.getRandomTime();
        String comment = faker.shakespeare().hamletQuote();
        Weibo_id from = Weibo_id.generateWeiboId();
        Weibo_id to = Weibo_id.generateWeiboId();
        WeiboAction weiboAction = new WeiboAction();
        weiboAction.setAction_type(action_type);
        weiboAction.setTime(time);
        weiboAction.setComment(comment);
        weiboAction.setFrom(from);
        weiboAction.setTo(to);
        return weiboAction;
    }

    public static void main(String[] args) {
        System.out.println(generateWeiboAction());
    }
}
