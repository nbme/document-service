package org.nbme.wbti.document.controller;

import org.nbme.wbti.document.service.impl.DocumentServiceFSImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileDownloadController {

	@Autowired
	DocumentServiceFSImpl documentServiceFS;

	@RequestMapping(value = "/downloadView", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "download nbme.jpg");
		List<String> fileNames = new ArrayList();
		File folder = new File("c:\\test");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
			fileNames.add(listOfFiles[i].getName());
			}
		}
		model.addAttribute("fileNames", fileNames);
		return "download";

	}

	@RequestMapping(value = "/file", method = RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFileFromRequestParam(@RequestParam String fileName) throws IOException {
		return new FileSystemResource(new File("c:\\test\\" + fileName));
	}

	@RequestMapping(value = "/files/{fileName}", method = RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
		return new FileSystemResource(new File("c:\\test\\" + fileName));
	}
	
}
