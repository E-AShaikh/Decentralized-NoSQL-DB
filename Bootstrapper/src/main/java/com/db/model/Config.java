package com.db.model;

//import org.example.load.balancer.LoadBalancer;

public class Config {
    private static Config instance;

    private int tcpStartingRange;
    private int udpStartingRange;
    private int bootstrapperUdpRange;
    private int loadBalanceTimeWindow;
    private int loadBalanceMaxRequests;
    private int bootstrapperTcpPort;
    private int containerNumbers;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
    public int getTcpStartingRange() {
        return tcpStartingRange;
    }

    public void setTcpStartingRange(int tcpStartingRange) {
        this.tcpStartingRange = tcpStartingRange;
    }

    public int getUdpStartingRange() {
        return udpStartingRange;
    }

    public void setUdpStartingRange(int udpStartingRange) {
        this.udpStartingRange = udpStartingRange;
    }

    public int getBootstrapperUdpRange() {
        return bootstrapperUdpRange;
    }

    public void setBootstrapperUdpRange(int bootstrapperUdpRange) {
        this.bootstrapperUdpRange = bootstrapperUdpRange;
    }

    public int getLoadBalanceTimeWindow() {
        return loadBalanceTimeWindow;
    }

    public void setLoadBalanceTimeWindow(int loadBalanceTimeWindow) {
        this.loadBalanceTimeWindow = loadBalanceTimeWindow;
    }

    public int getLoadBalanceMaxRequests() {
        return loadBalanceMaxRequests;
    }

    public void setLoadBalanceMaxRequests(int loadBalanceMaxRequests) {
        this.loadBalanceMaxRequests = loadBalanceMaxRequests;
    }

    public int getContainerNumbers() {
        return containerNumbers;
    }

    public void setContainerNumbers(int containerNumbers) {
        this.containerNumbers = containerNumbers;
    }

    public int getBootstrapperTcpPort() {
        return bootstrapperTcpPort;
    }

    public void setBootstrapperTcpPort(int bootstrapperTcpPort) {
        this.bootstrapperTcpPort = bootstrapperTcpPort;
    }
}

