package org.nbme.wbti.document.service.impl;

import org.nbme.wbti.document.dao.DocumentProcessDAO;
import org.nbme.wbti.document.model.DocumentProcess;
import org.nbme.wbti.document.service.DocumentProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rwang on 11/24/2015.
 */
@Service
public class DocumentProcessServiceImpl implements DocumentProcessService{
    @Autowired
    DocumentProcessDAO documentProcessDAO;

    @Override
    @Transactional
    public List<DocumentProcess> getDocumentProcesses() {
        return documentProcessDAO.getDocumentProcesses();
    }

    @Override
    @Transactional
    public DocumentProcess getDocumentProcess(int id) {
        return documentProcessDAO.getDocumentProcess(id);
    }

    @Override
    @Transactional
    public int addDocumentProcess(DocumentProcess documentProcess) {
       return documentProcessDAO.addDocumentProcess(documentProcess);
    }

    @Override
    @Transactional
    public void removeDocumentProcess(int id) {
        documentProcessDAO.removeDocumentProcess(id);

    }

    @Override
    @Transactional
    public void updateDocumentProcess(DocumentProcess documentProcess) {
        documentProcessDAO.updateDocumentProcess(documentProcess);

    }
}
