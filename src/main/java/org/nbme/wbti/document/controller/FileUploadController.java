package org.nbme.wbti.document.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nbme.wbti.document.configuration.DocumentServiceConfiguration;
import org.nbme.wbti.document.model.FileBucket;
import org.nbme.wbti.document.model.MultiFileBucket;
import org.nbme.wbti.document.util.FileValidator;
import org.nbme.wbti.document.util.MultiFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class FileUploadController{
	Log log = LogFactory.getLog(FileUploadController.class);
	private static String UPLOAD_LOCATION= "c:\\test\\";//DocumentServiceConfiguration.uploadTargetFolder;
	
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

	
	@RequestMapping(value={"/","/welcome"}, method = RequestMethod.GET)
	public String getHomePage(ModelMap model) {
		return "welcome";
	}

	@RequestMapping(value="/singleUpload", method = RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return "singleFileUploader";
	}

	@RequestMapping(value="/singleUpload", method = RequestMethod.POST)
	public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "singleFileUploader";
		} else {			
			System.out.println("Fetching file");
			MultipartFile multipartFile = fileBucket.getFile();
			log.info("upload target: " +UPLOAD_LOCATION);
			//Now do something with file...
			FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
			
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			return "success";
		}
	}
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") String fileName, ModelMap model)
	{
		String targetFilePath = UPLOAD_LOCATION + fileName;
		String sourceFilePath = "c:\\test\\source\\" + fileName;
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

		File targetFile = new File(targetFilePath);
		if(targetFile.exists())
			targetFile.delete();


		map.add("file", new FileSystemResource(sourceFilePath));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);

		ResponseEntity<String> result = restTemplate.exchange(
				"http://localhost:7001/du/fileUpload", HttpMethod.POST, requestEntity,
				String.class);
		return "ok";
	}

	@RequestMapping(value="/fileUpload", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public String fileUpload(@RequestParam("file") MultipartFile file, ModelMap model) throws IOException {

			MultipartFile multipartFile = file;
			log.info("upload target: " +UPLOAD_LOCATION);
			//Now do something with file...
			FileCopyUtils.copy(file.getBytes(), new File(UPLOAD_LOCATION + file.getOriginalFilename()));

			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			log.info("file uploaded successfully to " + UPLOAD_LOCATION);
			return "success";
	}

	
	@RequestMapping(value="/multiUpload", method = RequestMethod.GET)
	public String getMultiUploadPage(ModelMap model) {
		MultiFileBucket filesModel = new MultiFileBucket();
		model.addAttribute("multiFileBucket", filesModel);
		return "multiFileUploader";
	}

	@RequestMapping(value="/multiUpload", method = RequestMethod.POST)
	public String multiFileUpload(@Valid MultiFileBucket multiFileBucket, BindingResult result, ModelMap model) throws IOException {

		
		if (result.hasErrors()) {
			System.out.println("validation errors in multi upload");
			return "multiFileUploader";
		} else {			
			System.out.println("Fetching files");
			List<String> fileNames= new ArrayList<String>();
			
			//Now do something with file...
			for(FileBucket bucket : multiFileBucket.getFiles()){
				FileCopyUtils.copy(bucket.getFile().getBytes(), new File(UPLOAD_LOCATION + bucket.getFile().getOriginalFilename()));
				fileNames.add(bucket.getFile().getOriginalFilename());
			}
			
			model.addAttribute("fileNames", fileNames);
			return "multiSuccess";
		}
	}
	
	
	
}
