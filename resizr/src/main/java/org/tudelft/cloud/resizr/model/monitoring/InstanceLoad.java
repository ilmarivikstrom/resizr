package org.tudelft.cloud.resizr.model.monitoring;

public class InstanceLoad {
    private String instanceName;
    private Long jobCount;

    public InstanceLoad() {
    }
    public InstanceLoad(String instanceName, Long jobCount) {
        this.instanceName = instanceName;
        this.jobCount = jobCount;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getJobCount() {
        return jobCount;
    }

    public void setJobCount(Long jobCount) {
        this.jobCount = jobCount;
    }

    @Override
    public String toString() {
        return "InstanceLoad{" +
                "instanceName='" + instanceName + '\'' +
                ", jobCount=" + jobCount +
                '}';
    }
}
