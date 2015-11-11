package org.nbme.wbti.document.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nbme.wbti.document.model.FileBucket;
import org.nbme.wbti.document.model.MultiFileBucket;
import org.nbme.wbti.document.model.NBMEDocument;
import org.nbme.wbti.document.service.DocumentService;
import org.nbme.wbti.document.service.impl.DocumentServiceDBImpl;
import org.nbme.wbti.document.util.FileValidator;
import org.nbme.wbti.document.util.MultiFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/db")
public class DBFileUploadController {
	Log log = LogFactory.getLog(DBFileUploadController.class);
	
	@Autowired
	FileValidator fileValidator;

	@Autowired
	MultiFileValidator multiFileValidator;

	@Autowired
	private DocumentServiceDBImpl documentService;
	
	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
	   binder.setValidator(fileValidator);
	}

	@InitBinder("multiFileBucket")
	protected void initBinderMultiFileBucket(WebDataBinder binder) {
	   binder.setValidator(multiFileValidator);
	}

	@RequestMapping(value="/singleUpload", method = RequestMethod.POST)
	@Produces("application/json")
	public ResponseEntity singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			log.error("validation errors");
			throw new RuntimeException("file validation failed");
		} else {			
			log.debug("Fetching file");
			MultipartFile multipartFile = fileBucket.getFile();
			String fileName = multipartFile.getName();
			NBMEDocument nbmeDocument = new NBMEDocument(fileName, multipartFile);
			documentService.save(nbmeDocument);

			model.addAttribute("fileName", fileName);
			return new ResponseEntity("ok", HttpStatus.OK);
		}
	}
	@RequestMapping(value="/retrieve/{fileName}", method = RequestMethod.GET)
	@Produces("application/json")
	public ResponseEntity retrieveFile(@PathVariable String fileName, ModelMap model) throws IOException {
			InputStream inputStream = documentService.retrieve(fileName);
			if(inputStream != null) {
				OutputStream os = new FileOutputStream("c:\\test\\" + fileName);
				IOUtils.copy(inputStream, os);
			}

			return new ResponseEntity("ok", HttpStatus.OK);
		}
	}
