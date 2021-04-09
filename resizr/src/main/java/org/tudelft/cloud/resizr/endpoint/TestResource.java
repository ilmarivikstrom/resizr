package org.tudelft.cloud.resizr.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tudelft.cloud.resizr.model.Test;
import org.tudelft.cloud.resizr.service.receiver.TestService;
import org.tudelft.cloud.resizr.service.resource.management.ResourceManager;

import java.util.List;

@RestController
public class TestResource {

    @Autowired
    ResourceManager resourceManager;

    private final TestService testService;

    @Autowired
    public TestResource(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(path = "test")
    public List<Test> getTestList() {
        return testService.getTestList();
    }

    /**
     * The initial state of our system is having 2 slave instances and 1 redundant slave instance.
     */
    @GetMapping(path = "initialize")
    public void initializeSystem() {
        resourceManager.create();
        resourceManager.create();
        resourceManager.createRedundant();
    }
}
