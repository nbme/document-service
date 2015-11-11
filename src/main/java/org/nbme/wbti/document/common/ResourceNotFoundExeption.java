package org.nbme.wbti.document.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rwang on 11/10/2015.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExeption extends RuntimeException {
    public ResourceNotFoundExeption(String message)
    {
        super(message);
    }
}
