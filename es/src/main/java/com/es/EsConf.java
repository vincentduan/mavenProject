package com.es;

/**
 * @author vincent
 */
public class EsConf {
    private String ips;
    private String clusterName;
    private String port;

    public EsConf(String ips, String clusterName, String port) {
        super();
        this.ips = ips;
        this.clusterName = clusterName;
        this.port = port;
    }

    public String getIps() {
        return ips;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getPort() {
        return port;
    }

}
