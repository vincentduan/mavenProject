package fileUtils;

import java.io.*;

/**
 * 根据需求,直接调用静态方法start来执行操作
 * 参数:
 * rows 为多少行一个文件 int 类型
 * sourceFilePath 为源文件路径 String 类型
 * targetDirectoryPath 为文件分割后存放的目标目录 String 类型
 * ---分割后的文件名为索引号(从0开始)加'_'加源文件名,例如源文件名为test.txt,则分割后文件名为0_test.txt,以此类推
 */
public class SegFile {

    public static void start(int rows, String sourceFilePath, String targetDirectoryPath) {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetDirectoryPath);
        if (!sourceFile.exists() || rows <= 0 || sourceFile.isDirectory()) {
            System.out.println("源文件不存在或者输入了错误的行数");
            return;
        }
        if (targetFile.exists()) {
            if (!targetFile.isDirectory()) {
                System.out.println("目标文件夹错误,不是一个文件夹");
                return;
            }
        } else {
            targetFile.mkdirs();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(sourceFile));
            BufferedWriter bw = null;
            String str = "";
            String tempData = br.readLine();
            int i = 1, s = 0;
            while (tempData != null) {
                str += tempData + "\r\n";
                if (i % rows == 0) {
                    bw = new BufferedWriter(new FileWriter(new File(targetFile.getAbsolutePath() + "/" + s + "_" + sourceFile.getName())));
                    bw.write(str);
                    bw.close();
                    str = "";
                    s += 1;
                }
                i++;
                tempData = br.readLine();
            }
            if ((i - 1) % rows != 0) {
                bw = new BufferedWriter(new FileWriter(new File(targetFile.getAbsolutePath() + "/" + s + "_" + sourceFile.getName())));
                bw.write(str);
                bw.close();
                br.close();
                s += 1;
            }
            System.out.println("文件分割结束,共分割成了" + s + "个文件");
        } catch (Exception e) {
        }
    }

    //测试
    public static void main(String args[]) {
        SegFile.start(1, "E:/test/test2.rdf", "E:/test/test/");
    }
}


