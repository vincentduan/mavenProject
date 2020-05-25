package projectrobot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerateCSVData {
    public static void main(String[] args) throws IOException {
        // args[0] = "E:/test/";
        String outputPath = args[0];
        int date_count = Integer.parseInt(args[1]);
        File person_file = new File(outputPath + "person.random.csv");
        FileOutputStream fileOutputStream = new FileOutputStream(person_file);
        List<Person> person_list = Person.getPerson(date_count);
        for (Person person: person_list) {
            fileOutputStream.write(person.getCSVFormat().getBytes());
        }
    }
}
