package org.nbme.wbti.document;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Test;

import org.nbme.wbti.document.model.FileBucket;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by rwang on 11/19/2015.
 */
public class DocumentServiceIT {
    public static String sourceFilePath="c:\\test\\source\\nbme.jpg";
    public static String targetFilePath="c:\\test\\nbme.jpg";
    @Test
    public void testUploaSingleFile() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        messageConverters.clear();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        File targetFile = new File(targetFilePath);
        if(targetFile.exists())
            targetFile.delete();
        assertFalse(targetFile.exists());
        File file = new File(sourceFilePath);
        DiskFileItemFactory factory = new DiskFileItemFactory(100000000, file);
        DiskFileItem fileItem = new DiskFileItem("file", "application/octet-stream", false, file.getName(), (int) file.length() , file.getParentFile());
        fileItem.getOutputStream();
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        //map.add("fileBucket",  new FileSystemResource(sourceFilePath));
        FileBucket fileBucket = new FileBucket();
        fileBucket.setFile(multipartFile);
        map.add("file", new FileSystemResource(sourceFilePath));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.set("Accept", "text/plain");
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);

        ResponseEntity<String> result = restTemplate.exchange(
                "http://localhost:7001/du/fileUpload", HttpMethod.POST, requestEntity,
                String.class);
        assertTrue(targetFile.exists());
    }
}
