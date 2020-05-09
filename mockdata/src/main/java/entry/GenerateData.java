package entry;

import com.alibaba.fastjson.JSON;
import projectrobot.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateData {
    public static void main(String[] args) throws IOException {


        // args[0] = E:/test/
        String outputPath = args[0];
        int date_count = Integer.parseInt(args[1]);
        // person
        List<Person> person_list = Person.getPerson(date_count);
        String person = JSON.toJSONString(person_list);
        File person_file = new File(outputPath + "person.random");
        FileOutputStream fileOutputStream = new FileOutputStream(person_file);
        fileOutputStream.write(person.getBytes());

        // call
        List<Call> call_list = Stream.generate(() -> Call.generateCall()).limit(date_count).collect(Collectors.toList());
        String call = JSON.toJSONString(call_list);
        File call_file = new File(outputPath + "call.random");
        fileOutputStream = new FileOutputStream(call_file);
        fileOutputStream.write(call.getBytes());

        // loginAction_webo
        List<LoginAction> loginAction_weibo_list = Stream.generate(() -> LoginAction_weibo.generateLoginAction_weibo()).limit(date_count).collect(Collectors.toList());
        String loginAction_weibo = JSON.toJSONString(loginAction_weibo_list);
        File loginAction_weibo_file = new File(outputPath + "loginAction_weibo.random");
        fileOutputStream = new FileOutputStream(loginAction_weibo_file);
        fileOutputStream.write(loginAction_weibo.getBytes());
    }
}
