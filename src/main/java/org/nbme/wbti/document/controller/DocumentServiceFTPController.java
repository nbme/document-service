package org.nbme.wbti.document.controller;

/**
 * Created by RWang on 11/2/2015.
 */

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nbme.wbti.document.common.NBMEFtpClient;
import org.nbme.wbti.document.model.FileBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/ftp")
public class DocumentServiceFTPController {
    Log log = LogFactory.getLog(DocumentServiceFTPController.class);
    @Autowired
    NBMEFtpClient nbmeFtpClient;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {


        nbmeFtpClient.sendFile(fileDetail.getFileName());
        String message = String.format("File %s upload", fileDetail.getFileName());

        return Response.status(200).entity(message).build();
    }

    @RequestMapping(value="/singleUpload", method = RequestMethod.POST)
    @Produces("application/json")
    public ResponseEntity singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            log.error("validation errors");
            throw new RuntimeException("file validation error");
        } else {
           log.debug("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();

            //Now do something with file...
            nbmeFtpClient.sendFile(multipartFile.getOriginalFilename());

            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }
    }

}
