package fileUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;

/**
 * 使用LineIterator
 * 按行读取大文件
 */
public class BigFileReader {
    public static void main(String[] args) {
        File file = new File("E:/test/test2.rdf");
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                System.out.println(lineTxt);
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
}
