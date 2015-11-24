package org.nbme.wbti.document.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.nbme.wbti.document.dao.DocumentProcessDAO;
import org.nbme.wbti.document.model.DocumentProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rwang on 11/24/2015.
 */
@Repository
public class DocumentProcessDAOImpl implements DocumentProcessDAO {
    @Autowired
    SessionFactory sessionFactory;
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    @Override
    public List<DocumentProcess> getDocumentProcesses() {
        return getCurrentSession().createQuery("from org.nbme.wbti.document.model.DocumentProcess").list();
    }

    @Override
    public DocumentProcess getDocumentProcess(int id) {

        List results = getCurrentSession().createCriteria(DocumentProcess.class).add(Restrictions.eq("id", id)).list();
        return (DocumentProcess) results.get(0);
    }

    @Override
    public int addDocumentProcess(DocumentProcess documentProcess) {
        Serializable result = getCurrentSession().save(documentProcess);
        return (int)result;
    }

    @Override
    public void removeDocumentProcess(int id) {
        String hql = "DELETE FROM DocumentProcess WHERE id = :_id";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("_id", id);
        query.executeUpdate();
    }

    @Override
    public void updateDocumentProcess(DocumentProcess documentProcess) {


    }
}
