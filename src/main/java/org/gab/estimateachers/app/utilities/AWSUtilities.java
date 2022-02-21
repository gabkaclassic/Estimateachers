package org.gab.estimateachers.app.utilities;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component("awsUtilities")
public class AWSUtilities {
    
    private static String defaultFile = "question.png";
    
    private static String separator;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Value("${aws.key.access}")
    private String accessKey;
    
    @Value("${aws.key.secret}")
    private String secretKey;
    
    @Value("${aws.bucket.name}")
    private String bucketName;
    
    private AmazonS3 client;
    
    private Bucket bucket;
    
    private AWSUtilities(@Value("${aws.key.access}") String access,
                         @Value("${aws.key.secret}") String secret,
                         @Value("${aws.bucket.name}") String bucket,
                         @Value("${upload.path}") String upload) {
        
        accessKey = access;
        secretKey = secret;
        bucketName = bucket;
        uploadPath = upload;
        separator = System.getProperty("file.separator");
        
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    
        client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();
    
        if(!client.doesBucketExist(bucketName))
            client.createBucket(bucketName);
    
        this.bucket = client.listBuckets().get(0);
    }
    
    public void saveFile(File file) {
        
        if(Objects.nonNull(file))
            client.putObject(bucketName, file.getName(), file);
    }
    
    public void loadFiles(Collection<String> filenames) {
        
        if(Objects.isNull(filenames) || filenames.isEmpty())
            return;
        
        for(String filename: filenames) {
    
            S3Object object;
            
            if(Objects.nonNull(filename) && !filenames.isEmpty() && client.doesObjectExist(bucketName, filename))
                object = client.getObject(bucketName, filename);
            else
                object = client.getObject(bucketName, (filename = defaultFile));
            
            S3ObjectInputStream ois = object.getObjectContent();
            try {
                FileUtils.copyInputStreamToFile(ois, new File(uploadPath + separator + filename));
            } catch (IOException exception) {
                log.warn(String.format("The file was not load. Exception: %s, reason: %s, stack trace: %s",
                        exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
            }
        }
    }
    
    public void deleteFiles(Collection<String> filenames) {
    
        if(Objects.isNull(filenames) || filenames.isEmpty())
            return;
        
        for(String filename: filenames) {
            
            if(Objects.isNull(filename) || filename.isEmpty())
                continue;
            
            client.deleteObject(bucketName, filename);
            try {
                Files.delete(Path.of(filename));
            } catch (IOException exception) {
                log.warn(String.format("The file was not delete. Exception: %s, reason: %s, stack trace: %s",
                        exception.getMessage(), exception.getCause(), Arrays.toString(exception.getStackTrace())));
            }
        }
        
    }
}
