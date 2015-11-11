package org.nbme.wbti.document.service.impl;

import org.apache.commons.io.IOUtils;
import org.nbme.wbti.document.dao.DocumentServiceDAO;
import org.nbme.wbti.document.model.NBMEDocument;
import org.nbme.wbti.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * Created by RWang on 11/2/2015.
 */
@Service
public class DocumentServiceFSImpl implements DocumentService {

    @Override
    public void save(NBMEDocument document) throws IOException {
       FileCopyUtils.copy(document.getFileData().getBytes(), new File("c:\\test\\" + document.getFilename()));
    }

    @Override
    public InputStream retrieve(String fileName) throws IOException {
        return new FileInputStream("c:\\test\\" + fileName);
    }
}
