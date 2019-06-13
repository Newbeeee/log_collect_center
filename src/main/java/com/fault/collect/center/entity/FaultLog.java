package com.fault.collect.center.entity;

public class FaultLog {
    int id;
    String node;
    String service;
    String logPath;
    String storagePath;
    String missionId;
    String redisId;
    String content;
    String host;
    int notEmpty;
    boolean match = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getRedisId() {
        return redisId;
    }

    public void setRedisId(String redisId) {
        this.redisId = redisId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public int getNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(int notEmpty) {
        this.notEmpty = notEmpty;
    }

//    String node;
//    String service;
//    String logPath;
//    String storagePath;
//    String missionId;
//    String redisId;
//    String content;
//    String host;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + " | ");
        sb.append("node: " + node + " | ");
        sb.append("service: " + service + " | ");
        sb.append("host: " + host + " | ");
        sb.append("content: " + content + " | ");
        return super.toString();
    }
}
