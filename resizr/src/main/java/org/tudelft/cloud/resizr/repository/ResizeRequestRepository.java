package org.tudelft.cloud.resizr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr.model.jpa.ResizeRequest;

import java.util.List;

@Repository
public interface ResizeRequestRepository extends PagingAndSortingRepository<ResizeRequest, String> {
    // Use this if we are overwhelmed in the Job Dispatcher
    // Page<ResizeRequest> findAllByIsProcessedIsFalse(Pageable pageable);

    List<ResizeRequest> findAllByIsProcessedIsFalse();

    Long countAllByIsProcessedIsFalse();


}
