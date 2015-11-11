package org.nbme.wbti.document.dao;

import org.nbme.wbti.document.model.NBMEDocument;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RWang on 11/2/2015.
 */
@Repository
public interface DocumentServiceDAO {
    void saveDocument(NBMEDocument nbmeDocument) throws IOException;
    InputStream retrieveDocument(String fileName) throws IOException;
    void removeDocument(String fileName);
}
