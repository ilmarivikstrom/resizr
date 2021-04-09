package org.tudelft.cloud.resizr_slave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Blob;

@Entity
public class CommunicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    private Long resizeId;

    private String instanceName;

    private Double x;

    private Double y;

    private String imageName;

    private Blob initialImageBlob;

    private String initialImageHash;

    private Blob resizedImageBlob;

    private Long dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Long getResizeId() {
        return resizeId;
    }

    public void setResizeId(Long resizeId) {
        this.resizeId = resizeId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Blob getInitialImageBlob() {
        return initialImageBlob;
    }

    public void setInitialImageBlob(Blob initialImageBlob) {
        this.initialImageBlob = initialImageBlob;
    }

    public String getInitialImageHash() {
        return initialImageHash;
    }

    public void setInitialImageHash(String initialImageHash) {
        this.initialImageHash = initialImageHash;
    }

    public Blob getResizedImageBlob() {
        return resizedImageBlob;
    }

    public void setResizedImageBlob(Blob resizedImageBlob) {
        this.resizedImageBlob = resizedImageBlob;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "CommunicationEntity{" +
                "id=" + id +
                ", jobId='" + jobId + '\'' +
                ", resizeId=" + resizeId +
                ", instanceName='" + instanceName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", imageName='" + imageName + '\'' +
                ", initialImageBlob=" + initialImageBlob +
                ", initialImageHash='" + initialImageHash + '\'' +
                ", resizedImageBlob=" + resizedImageBlob +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
