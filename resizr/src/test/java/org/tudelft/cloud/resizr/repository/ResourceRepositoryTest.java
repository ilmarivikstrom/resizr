package org.tudelft.cloud.resizr.repository;

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

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ResizrApplicationTest.class)
@AutoConfigurationPackage
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ResourceRepositoryTest {

    @Autowired
    ResourceRepository resourceRepository;

    @Test
    public void saveResource() {
        Resource resource = new Resource();
        resource.setId(123L);
        resource.setName("testResource");
        resource.setUrl("myTestResourceUrl");
        System.out.println(resourceRepository.save(resource));
    }
}
