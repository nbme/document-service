package org.nbme.wbti.document.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.nbme.wbti.document.model.FileBucket;
import org.nbme.wbti.document.model.MultiFileBucket;

@Component
public class MultiFileValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {
		return MultiFileBucket.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MultiFileBucket multiBucket = (MultiFileBucket) obj;
		
		int index=0;
		
		for(FileBucket file : multiBucket.getFiles()){
			if(file.getFile()!=null){
				if (file.getFile().getSize() == 0) {
					errors.rejectValue("files["+index+"].file", "missing.file");
				}
			}
			index++;
		}
		
	}
}