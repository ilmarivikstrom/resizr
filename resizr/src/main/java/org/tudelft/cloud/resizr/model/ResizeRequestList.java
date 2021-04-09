package org.tudelft.cloud.resizr.model;

import org.tudelft.cloud.resizr.model.jpa.ResizeRequest;

import java.util.List;

public class ResizeRequestList {
    private List<ResizeRequest> resizeRequestList;

    public List<ResizeRequest> getResizeRequestList() {
        return resizeRequestList;
    }

    public void setResizeRequestList(List<ResizeRequest> resizeRequestList) {
        this.resizeRequestList = resizeRequestList;
    }

    @Override
    public String toString() {
        return "ResizeRequestList{" +
                "resizeRequestList=" + resizeRequestList +
                '}';
    }
}
