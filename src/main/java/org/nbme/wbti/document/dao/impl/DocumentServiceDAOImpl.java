package org.nbme.wbti.document.dao.impl;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.nbme.wbti.document.common.ResourceNotFoundExeption;
import org.nbme.wbti.document.configuration.Constants;
import org.nbme.wbti.document.dao.DocumentServiceDAO;
import org.nbme.wbti.document.model.NBMEDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RWang on 11/2/2015.
 */
@Repository
public class DocumentServiceDAOImpl implements DocumentServiceDAO {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void saveDocument(NBMEDocument nbmeDocument) throws IOException {
        GridFS fs = new GridFS(mongoTemplate.getDb(), Constants.COLL_NAME);
        GridFSInputFile gfsFile = fs.createFile(nbmeDocument.getFileData().getInputStream());
        gfsFile.setFilename(nbmeDocument.getFilename());
        gfsFile.save();
    }

    @Override
    public InputStream retrieveDocument(String fileName) throws IOException {
        // get image file by it's filename
        GridFS gfsPhoto = new GridFS(mongoTemplate.getDb(), Constants.COLL_NAME);
        GridFSDBFile fileForOutput = gfsPhoto.findOne(fileName);
        if(fileForOutput != null)
            return fileForOutput.getInputStream();
        throw new ResourceNotFoundExeption(fileName + " not found!");
    }

    @Override
    public void removeDocument(String fileName) {

    }
}
