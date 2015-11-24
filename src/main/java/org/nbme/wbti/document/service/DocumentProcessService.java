package org.nbme.wbti.document.service;

import org.nbme.wbti.document.model.DocumentProcess;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rwang on 11/24/2015.
 */
@Service
public interface DocumentProcessService {
    List<DocumentProcess> getDocumentProcesses();
    DocumentProcess getDocumentProcess(int id);
    int addDocumentProcess(DocumentProcess documentProcess);
    void removeDocumentProcess(int id);
    void updateDocumentProcess(DocumentProcess documentProcess);
}
