package org.tudelft.cloud.resizr_slave.service;

import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr_slave.model.CommunicationEntity;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class ImageResizerService {

    public CommunicationEntity getResizedJob(CommunicationEntity resizeJob) throws IOException, SQLException {

        InputStream inputImageInputStream = blobToInputStream(resizeJob.getInitialImageBlob());
        BufferedImage inputImage = ImageIO.read(inputImageInputStream);
        BufferedImage outputImage = new BufferedImage(resizeJob.getX().intValue(), resizeJob.getY().intValue(), inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, resizeJob.getX().intValue(), resizeJob.getY().intValue(), null);
        g2d.dispose();
        Blob resizedImageBlob = imageToBlob(outputImage);
        resizeJob.setResizedImageBlob(new SerialBlob(resizedImageBlob));
        return resizeJob;
    }

    private InputStream blobToInputStream(Blob blob) throws SQLException {
        byte[] buff = blob.getBytes(1,(int)blob.length());
        InputStream inputImageInputStream = new ByteArrayInputStream(buff);
        return inputImageInputStream;
    }

    private Blob imageToBlob(BufferedImage image) throws IOException, SQLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "png", baos );
        baos.flush();
        byte[] outputImageByteArray = baos.toByteArray();
        baos.close();
        return new SerialBlob(outputImageByteArray);

    }
}
