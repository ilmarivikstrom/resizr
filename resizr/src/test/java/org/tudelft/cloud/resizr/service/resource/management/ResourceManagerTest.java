package org.tudelft.cloud.resizr.service.resource.management;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tudelft.cloud.resizr.config.ResizrApplicationTest;
import org.tudelft.cloud.resizr.model.jpa.Resource;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ResizrApplicationTest.class)
@AutoConfigurationPackage
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResourceManagerTest {

    @Autowired
    ResourceManager resourceManager;

    private Resource testResource;

    @Test
    public void createResource() {
        testResource = resourceManager.create();
    }
}
