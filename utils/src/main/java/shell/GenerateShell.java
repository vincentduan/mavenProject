package shell;

import com.alibaba.fastjson.JSONObject;
import fileUtils.ReadWriteNio;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by vincent on 2020-5-28 15:03
 * 读取原始shell, 生成新的shell
 */
public class GenerateShell {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("开始生成shell脚本");

        String shell_dir = "Z:\\ddy\\tools\\";
        // 输入原始shell
        File shell_in = new File(shell_dir + "file2ES.sh");
        // 生成新的shell
        File file = new File(shell_dir + "file2ES-new.sh");

        if (file.exists()) {
            file.delete();
        }

        FileChannel shell_out = new RandomAccessFile(file, "rws").getChannel();

        // 读取配置文件
        String auto_import_file = "C:\\Users\\user\\Desktop\\auto_import.json";
        JSONObject auto_import_json = JSONObject.parseObject(getFirstLine(auto_import_file));

        // 读取 awk
        String awk_String = auto_import_json.getString("awk");

        // 读取 mapping
        String mapping_string = auto_import_json.getString("mapping");

        // 读取indexName
        String indexName = auto_import_json.getString("indexName");

        // 读取data_dir
        String rawDataPath = auto_import_json.getString("rawDataPath");

        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(shell_in, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                String lineTxt_trim = lineTxt.trim();

                ReadWriteNio.writeFileByLine(shell_out, lineTxt + "\n");

                // 注意：步骤顺序不能乱
                // 1. indexName
                if (lineTxt_trim.equals("# indexName")) {
                    System.out.println("遇到indexName插入位置");
                    ReadWriteNio.writeFileByLine(shell_out, "indexName=\"" + indexName + "\"\n");
                }

                // 2. 生成mapping
                if (lineTxt_trim.equals("# mapping")) {
                    System.out.println("遇到mapping插入位置");
                    ReadWriteNio.writeFileByLine(shell_out, mapping_string + "\n");
                }

                // 3. 待处理的原始数据目录
                if (lineTxt_trim.equals("# for file in `ls /*`")) {
                    System.out.println("遇到for file in 插入位置");
                    String line = "for file in `ls " + rawDataPath + "`";
                    ReadWriteNio.writeFileByLine(shell_out, line + "\n");
                }

                // 4. 生成awk
                if (lineTxt_trim.equals("# awk")) {
                    System.out.println("遇到awk插入位置");
                    ReadWriteNio.writeFileByLine(shell_out, "\t\t" + awk_String + "\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (it != null) {
                try {
                    it.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("生成shell脚本成功");
    }

    /**
     * 读取配置文件内容 第一行
     *
     * @param fileName 文件路径
     * @return 配置文件auto_import json格式
     */
    private static String getFirstLine(String fileName) {
        File file = new File(fileName);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
