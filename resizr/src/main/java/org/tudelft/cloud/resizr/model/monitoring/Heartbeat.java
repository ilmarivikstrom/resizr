package org.tudelft.cloud.resizr.model.monitoring;

public class Heartbeat {

    boolean flatline = false;
    String status;
    Long instanceId;
    String instanceName;
    String instanceUrl;

    public boolean isFlatline() {
        return flatline;
    }

    public void setFlatline(boolean flatline) {
        this.flatline = flatline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "flatline=" + flatline +
                ", status='" + status + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", instanceUrl='" + instanceUrl + '\'' +
                '}';
    }
}
