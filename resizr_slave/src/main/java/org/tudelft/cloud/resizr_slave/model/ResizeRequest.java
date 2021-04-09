package org.tudelft.cloud.resizr_slave.model;

import javax.persistence.*;

@Entity
public class ResizeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String imageName;

    private Double x;

    private Double y;

    private String initialImageHash;

    private String jobId;

    private boolean isProcessed = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getInitialImageHash() {
        return initialImageHash;
    }

    public void setInitialImageHash(String initialImageHash) {
        this.initialImageHash = initialImageHash;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    @Override
    public String toString() {
        return "ResizeRequest{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", initialImageHash='" + initialImageHash + '\'' +
                ", jobId='" + jobId + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
