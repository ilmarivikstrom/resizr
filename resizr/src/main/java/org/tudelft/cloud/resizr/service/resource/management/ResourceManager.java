package org.tudelft.cloud.resizr.service.resource.management;

import org.tudelft.cloud.resizr.model.jpa.Resource;

public interface ResourceManager {

    Resource create();

    Resource createRedundant();

    void delete(Resource resource);
}
