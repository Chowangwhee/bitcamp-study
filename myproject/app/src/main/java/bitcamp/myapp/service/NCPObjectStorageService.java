package bitcamp.myapp.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class NCPObjectStorageService implements StorageService {

    private final String endPoint;
    private final String regionName;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final String boardPrefix;
    private final AmazonS3 s3;

    public NCPObjectStorageService() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("storage.properties")) {
            if (input == null) {
                throw new StorageServiceException("storage.properties file not found in classpath.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new StorageServiceException("Error loading storage.properties file.", e);
        }

        endPoint = properties.getProperty("end.point");
        regionName = properties.getProperty("region.name");
        accessKey = properties.getProperty("access.key");
        secretKey = properties.getProperty("secret.key");
        bucketName = properties.getProperty("bucket.name");
        boardPrefix = properties.getProperty("board.prefix");

        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.endPoint,
                        this.regionName))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(this.accessKey, this.secretKey)))
                .build();
    }

    @Override
    public String upload(String filename, InputStream fileIn) throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("application/octet-stream"); // Set appropriate content type

        String key = boardPrefix + filename; // Prepend the prefix to the filename
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, fileIn, objectMetadata);

        try {
            s3.putObject(putObjectRequest);
            return getBucketUrl(key);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private String getBucketUrl(String key) {
        return String.format("%s/%s/%s", this.endPoint, this.bucketName, key);
    }

    @Override
    public void download(String key, OutputStream fileOut) throws Exception {
        S3Object s3Object = s3.getObject(bucketName, key);
        try (S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {

            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = objectInputStream.read(bytesArray)) != -1) {
                fileOut.write(bytesArray, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new StorageServiceException(e);
        }
    }

    @Override
    public void delete(String key) throws Exception {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
        try {
            s3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageServiceException(e);
        }
    }
}