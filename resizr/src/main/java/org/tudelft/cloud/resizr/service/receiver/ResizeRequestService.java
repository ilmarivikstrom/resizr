package org.tudelft.cloud.resizr.service.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.jpa.ResizeRequest;
import org.tudelft.cloud.resizr.repository.ResizeRequestRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
public class ResizeRequestService {

    private final ResizeRequestRepository resizeRequestRepository;

    @Autowired
    public ResizeRequestService(ResizeRequestRepository resizeRequestRepository) {
        this.resizeRequestRepository = resizeRequestRepository;
    }

    public String splitAndStoreResizeRequests(List<ResizeRequest> resizeRequestList) {
        String jobId = UUID.randomUUID().toString();
        for (ResizeRequest request : resizeRequestList) {
            String hash = getHash(request);
            request.setInitialImageHash(hash);
        }
        resizeRequestList.forEach(it -> it.setJobId(jobId));

        resizeRequestRepository.saveAll(resizeRequestList);
        return jobId;
    }

    private String getHash(ResizeRequest request) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get("src/main/resources/" + request.getImageName())));
            byte[] digest = md.digest();
            String hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return hash;
        } catch (NoSuchAlgorithmException n) {
            n.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
