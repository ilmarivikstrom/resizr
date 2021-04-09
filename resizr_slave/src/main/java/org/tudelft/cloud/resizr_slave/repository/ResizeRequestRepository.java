package org.tudelft.cloud.resizr_slave.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr_slave.model.ResizeRequest;


@Repository
public interface ResizeRequestRepository extends PagingAndSortingRepository<ResizeRequest, String> {
}
