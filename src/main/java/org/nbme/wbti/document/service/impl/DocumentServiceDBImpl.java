package org.nbme.wbti.document.service.impl;

import org.nbme.wbti.document.dao.DocumentServiceDAO;
import org.nbme.wbti.document.model.NBMEDocument;
import org.nbme.wbti.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RWang on 11/2/2015.
 */
@Service
public class DocumentServiceDBImpl implements DocumentService {
    @Autowired
    private DocumentServiceDAO documentServiceDAO;
    @Override
    public void save(NBMEDocument document) throws IOException {
        documentServiceDAO.saveDocument(document);
    }

    @Override
    public InputStream retrieve(String fileName) throws IOException {
        return documentServiceDAO.retrieveDocument(fileName);
    }
}
