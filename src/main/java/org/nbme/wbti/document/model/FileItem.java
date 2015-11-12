package org.nbme.wbti.document.model;

import java.io.File;

/**
 * Created by rwang on 11/12/2015.
 */
public class FileItem {
    private String name;
    private File file;

    public FileItem(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
