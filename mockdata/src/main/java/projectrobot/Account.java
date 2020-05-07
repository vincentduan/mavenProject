package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;

public class Account {
    @JSONField(name="datasource")
    private String datasource;

    public Account() {
    }

    public Account(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
