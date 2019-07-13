package cn.ac.iie.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vincent on 2019-7-13 16:17
 */
@Component
@ConfigurationProperties("es")
public class EsConf {

    private List<String> ips;
    private String clusterName;
    private int port;

    @Override
    public String toString() {
        return "EsConf{" +
                "ips=" + ips +
                ", clusterName='" + clusterName + '\'' +
                ", port=" + port +
                '}';
    }

    public List<String> getIps() {
        return ips;
    }

    public String getClusterName() {
        return clusterName;
    }

    public int getPort() {
        return port;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
