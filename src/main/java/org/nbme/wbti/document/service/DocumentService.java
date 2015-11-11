package org.nbme.wbti.document.service;

import org.nbme.wbti.document.model.NBMEDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RWang on 11/2/2015.
 */
@Service
public interface DocumentService {
    public void save(NBMEDocument document) throws IOException;
    public InputStream retrieve(String fileName) throws IOException;
}
