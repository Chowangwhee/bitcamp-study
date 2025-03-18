package bitcamp.myapp.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface StorageService {
    String upload(String filename, InputStream fileIn) throws Exception;
    void download(String filename, OutputStream fileOut) throws Exception;
    void delete(String filename) throws Exception;
}