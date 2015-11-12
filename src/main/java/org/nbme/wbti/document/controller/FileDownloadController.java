package org.nbme.wbti.document.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nbme.wbti.document.model.FileItem;
import org.nbme.wbti.document.service.impl.DocumentServiceFSImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileDownloadController {
	Log log = LogFactory.getLog(FileDownloadController.class);
	@Autowired
	DocumentServiceFSImpl documentServiceFS;
	public File folder = new File("c:\\test");
	@RequestMapping(value = "/downloadView", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "download nbme.jpg");

//		model.addAttribute("fileNames", fileNames);
		return "download";

	}
	@ModelAttribute("allFiles")
	public List<FileItem> populateFiles()
	{
		List<FileItem> files = new ArrayList();


		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String path = listOfFiles[i].getPath();
				String name = path.substring(path.lastIndexOf("\\")+1);
				files.add(new FileItem(name, listOfFiles[i]));
			}
		}
		return files;
	}
	/*@RequestMapping(value = "/file", method = RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFileFromRequestParam(@RequestParam String fileName) throws IOException {
		return new FileSystemResource(new File("c:\\test\\" + fileName));
	}*/

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public @ResponseBody
	void download(@RequestParam String fileName, HttpServletResponse response) throws IOException {

			try {
				File file = new File("c:\\test\\" + fileName);
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
				response.setContentLength((new Long(file.length()).intValue()));
				response.setHeader("content-Disposition", "attachment; filename=" + fileName);// "attachment;filename=test.xls"
				// copy it to response's OutputStream
				IOUtils.copyLarge(new FileInputStream(file), response.getOutputStream());
			} catch (IOException ex) {
				log.info("Error writing file to output stream. Filename was '" + fileName + "'");
				throw new RuntimeException("IOError writing file to output stream");
			}
	}
	@RequestMapping(value = "/files/{fileName}", method = RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
		return new FileSystemResource(new File("c:\\test\\" + fileName));
	}
	
}
