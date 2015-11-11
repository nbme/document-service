package org.nbme.wbti.document.controller;

import org.nbme.wbti.document.model.FileBucket;
import org.nbme.wbti.document.model.MultiFileBucket;
import org.nbme.wbti.document.service.impl.DocumentServiceFSImpl;
import org.nbme.wbti.document.util.FileValidator;
import org.nbme.wbti.document.util.MultiFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileDownloadController {

	@Autowired
	DocumentServiceFSImpl documentServiceFS;
	
	@Autowired
	private FileValidator fileValidator;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MultiFileValidator multiFileValidator;

	
	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
	   binder.setValidator(fileValidator);
	}


	@InitBinder("multiFileBucket")
	protected void initBinderMultiFileBucket(WebDataBinder binder) {
	   binder.setValidator(multiFileValidator);
	}

	@RequestMapping(value = "/files/{fileName}", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
		return new FileSystemResource(new File("c:\\test\\" + fileName));
	}
	
}
