package ETL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Extract {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("开始读取文件，请等待...");

        JSONObject jsonObject = JSONObject.parseObject("{\"fileName\": \"person.random.csv\",\"filePath\": \"E:/test/\",\"fileType\": \"csv\",\"delimiter\": \"\\t\",\"fields\": [{\"raw_field_num\": \"0\",\"raw_field_name\": \"c1\",\"es_field_name\": \"idcard\"},{\"raw_field_num\": \"1\",\"raw_field_name\": \"c2\",\"es_field_name\": \"city\"},{\"raw_field_num\": \"3\",\"raw_field_name\": \"c4\",\"es_field_name\": \"age\"}]}");
        // 文件名
        String fileName = jsonObject.getString("fileName");
        // 文件路径
        String filePath = jsonObject.getString("filePath");
        // 文件类型
        String fileType = jsonObject.getString("fileType");
        // 分隔符
        String delimiter = jsonObject.getString("delimiter");
        // ES indexName
        String es_indexName = jsonObject.getString("es_indexName");

        JSONArray fields = jsonObject.getJSONArray("fields");
        ArrayList<Fields> fields_list = new ArrayList();
        for(int i = 0; i < fields.size(); i++) {
            JSONObject jsonObject1 = fields.getJSONObject(i);
            Fields fields1 = new Fields();
            fields1.setRaw_field_name(jsonObject1.getString("raw_field_name"));
            fields1.setRaw_field_num(Integer.parseInt(jsonObject1.getString("raw_field_num")));
            fields1.setEs_field_name(jsonObject1.getString("es_field_name"));
            fields_list.add(fields1);
        }
        File file = new File(filePath + fileName);
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                String[] split = line.split(delimiter);
                JSONObject result = new JSONObject();
                for(Fields fields1 : fields_list){
                    String raw_field = split[fields1.getRaw_field_num()];
                    String es_field_name = fields1.getEs_field_name();
                    result.put(es_field_name, raw_field);
                }
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line){
        try {
            //write on file head
            //fcout.write(wBuffer.wrap(line.getBytes()));
            //wirte append file on foot
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static class Fields {
        private int raw_field_num;
        private String raw_field_name;
        private String es_field_name;
        
        public int getRaw_field_num() {
            return raw_field_num;
        }

        public void setRaw_field_num(int raw_field_num) {
            this.raw_field_num = raw_field_num;
        }

        public String getRaw_field_name() {
            return raw_field_name;
        }

        public void setRaw_field_name(String raw_field_name) {
            this.raw_field_name = raw_field_name;
        }

        public String getEs_field_name() {
            return es_field_name;
        }

        public void setEs_field_name(String es_field_name) {
            this.es_field_name = es_field_name;
        }
    }

}
