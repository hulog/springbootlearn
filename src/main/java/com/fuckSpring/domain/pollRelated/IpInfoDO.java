package com.fuckSpring.domain.pollRelated;

import java.io.Serializable;

/**
 * Created by upsmart on 17-6-5.
 */
public class IpInfoDO implements Serializable{

    private static final long serialVersionUID = -8937194876835217366L;

    private String ip;
    private Integer port;
    private String addr;

    public IpInfoDO() {
    }

    public IpInfoDO(String ip, Integer port, String addr) {
        this.ip = ip;
        this.port = port;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "IpInfoDO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", addr='" + addr + '\'' +
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
