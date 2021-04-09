package org.tudelft.cloud.resizr.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tudelft.cloud.resizr.model.ResizeRequestList;
import org.tudelft.cloud.resizr.service.receiver.ResizeRequestService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class RequestReceiver {

    private final ResizeRequestService resizeRequestService;

    @Autowired
    public RequestReceiver(ResizeRequestService resizeRequestService) {
        this.resizeRequestService = resizeRequestService;
    }

    @PostMapping("/resizr/request")
    public String receiveRequest(@RequestBody ResizeRequestList resizeRequestList) {
        // push to DB
        System.out.println(resizeRequestList);
        return resizeRequestService.splitAndStoreResizeRequests(resizeRequestList.getResizeRequestList());
    }

}
