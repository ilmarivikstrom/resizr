package org.tudelft.cloud.resizr.service.resource.management;

import org.tudelft.cloud.resizr.model.jpa.Resource;

public interface RemoteResourceManager {

    Resource createRemoteResource();

    void deleteRemoteResource(Resource resource);
}
