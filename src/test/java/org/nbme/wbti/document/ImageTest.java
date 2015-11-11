package org.nbme.wbti.document;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by RWang on 11/2/2015.
 */
public class ImageTest {

    public static final String HOST = "localhost";
    public static final String DB_NAME = "imagedb";
    public static final String COLL_NAME = "nbmeColl";
    public static final String NEW_IMAGE = "robin-image";
    public static final String IMAGE_FILE_PATH = "c:\\test\\source\\nbme";
    public static final String PHOTO_NAMESPACE = "photo";
    public static final String NEW_IMAGE_FILE_PATH = "c:\\test\\new_nbme.jpg";

    @Test
    public void testSaveImage() throws IOException, NoSuchAlgorithmException {

            Mongo mongo = new Mongo(HOST, 27017);
            DB db = mongo.getDB(DB_NAME);
//            DBCollection collection = db.getCollection(COLL_NAME);
//
//            String newFileName = NEW_IMAGE;

            File imageFile = new File(IMAGE_FILE_PATH);

            // create a "photo" namespace
            GridFS gfsPhoto = new GridFS(db, COLL_NAME);

            // get image file from local drive
            GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

            // set a new filename for identify purpose
            gfsFile.setFilename("nbme");

            // save the image file into mongoDB
            gfsFile.save();

            // print the result
            DBCursor cursor = gfsPhoto.getFileList();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

            // get image file by it's filename
            GridFSDBFile imageForOutput = gfsPhoto.findOne("nbme");

            // save it into a new image file
            imageForOutput.writeTo(NEW_IMAGE_FILE_PATH);

            // remove the image file from mongoDB
//            gfsPhoto.remove(gfsPhoto.findOne(newFileName));
            SHA1Compare.assertEqualsSHA1(IMAGE_FILE_PATH, NEW_IMAGE_FILE_PATH);
            System.out.println("Done");
    }
}
