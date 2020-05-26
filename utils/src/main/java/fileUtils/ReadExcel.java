package fileUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;

/**
 * @author vincent
 * @time 2020-05-26 22:10
 */
public class ReadExcel {
    public static void main(String[] args) {
        String fileName = "/Users/duandingyang/Desktop/excel-test.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    public static class DemoData {
        private String name;
        private String age;
        private String gender;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
    public static class DemoDataListener extends AnalysisEventListener<DemoData> {

        @Override
        public void invoke(DemoData data, AnalysisContext analysisContext) {
            System.out.println(JSON.toJSONString(data));
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            System.out.println("所有数据解析完成");
        }
    }
}
