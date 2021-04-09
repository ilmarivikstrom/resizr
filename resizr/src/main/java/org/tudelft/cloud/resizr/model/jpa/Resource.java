package org.tudelft.cloud.resizr.model.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String name;
    private boolean available;
    private Long queueSize;
    private boolean isRedundant;
    private boolean markedForKill = false;

    public Resource() {
    }

    public Resource(String url, String name, boolean available, Long queueSize, boolean isRedundant, boolean markedForKill) {
        this.url = url;
        this.name = name;
        this.available = available;
        this.queueSize = queueSize;
        this.isRedundant = isRedundant;
        this.markedForKill = markedForKill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Long queueSize) {
        this.queueSize = queueSize;
    }

    public boolean isRedundant() {
        return isRedundant;
    }

    public void setRedundant(boolean redundant) {
        isRedundant = redundant;
    }

    public boolean isMarkedForKill() {
        return markedForKill;
    }

    public void setMarkedForKill(boolean markedForKill) {
        this.markedForKill = markedForKill;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", available=" + available +
                ", queueSize=" + queueSize +
                ", isRedundant=" + isRedundant +
                ", markedForKill=" + markedForKill +
                '}';
    }
}
