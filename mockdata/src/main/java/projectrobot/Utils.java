package projectrobot;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    static Faker faker = new Faker();

    static List<String> idcard_list = new ArrayList<>();
    static List<String> telephone_list = new ArrayList<>();

    static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    static String[] action_type = new String[]{"评论", "回复"};

    static {
        File idcard_file = new File("data/idcard.txt");
        File telephone_file = new File("data/telephone.txt");
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(idcard_file, "UTF-8");
            while (it.hasNext()) {
                String idcard = it.nextLine();
                idcard_list.add(idcard);
            }
            it = FileUtils.lineIterator(telephone_file, "UTF-8");
            while (it.hasNext()) {
                String telephone = it.nextLine();
                telephone_list.add(telephone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(it != null){
                try {
                    it.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从data文件中随机获取身份证号
     * @return
     */
    public static String getRandomIdcard(){
        return idcard_list.get(faker.number().numberBetween(0, idcard_list.size()));
    }

    /**
     * 从data文件中随机获取手机号
     * @return
     */
    public static String getRandomTelephone() {
        return telephone_list.get(faker.number().numberBetween(0, telephone_list.size()));
    }

    /**
     * 随机生成时间 格式：1984-04-16T03:35:38
     * @return
     */
    public static String getRandomTime() {
        String time = simpleDateFormat.format(faker.date().birthday());
        return time;
    }

    public static String timeAddSecond(Date date, int second) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return date2String(calendar.getTime());
    }

    public static Date string2Date(String time) {
        try {
            return fastDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2String(Date date) {
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) {

        String time = simpleDateFormat.format(new Date());
        System.out.println(time);
        System.out.println(getRandomTime());
    }


}
