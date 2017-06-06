package com.fuckSpring.domain.pollRelated;

/**
 * Created by upsmart on 17-6-5.
 */
public class IpInfoDO {

    private String ip;
    private Integer port;

    public IpInfoDO(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "IpInfoDO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
