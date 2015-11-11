package org.nbme.wbti.document.model;

/**
 * Created by RWang on 11/2/2015.
 */
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class NBMEDocument {
    private String filename;
    private MultipartFile fileData;

    public NBMEDocument(String fileName, MultipartFile fileData) {
        this.filename = fileName;
        this.fileData = fileData;

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
}