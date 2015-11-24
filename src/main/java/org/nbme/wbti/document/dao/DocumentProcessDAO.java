package org.nbme.wbti.document.dao;

import org.nbme.wbti.document.model.DocumentProcess;

import java.util.List;

/**
 * Created by rwang on 11/24/2015.
 */
public interface DocumentProcessDAO {
    List<DocumentProcess> getDocumentProcesses();
    DocumentProcess getDocumentProcess(int id);
    int addDocumentProcess(DocumentProcess documentProcess);
    void removeDocumentProcess(int id);
    void updateDocumentProcess(DocumentProcess documentProcess);
}
