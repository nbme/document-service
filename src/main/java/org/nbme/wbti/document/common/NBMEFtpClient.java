package org.nbme.wbti.document.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by RWang on 11/4/2015.
 */
@Component
public class NBMEFtpClient {

    private String remoteDirectory;
    private String localDirectory;
    private StandardFileSystemManager manager = new StandardFileSystemManager();;
    private String sftpUriRoot;
    private Log log = LogFactory.getLog(NBMEFtpClient.class);

    @Autowired
    private MessageSource messageSource;
    @PostConstruct
    private void init() throws IOException {
        String serverAddress = messageSource.getMessage("ftp.server.address", null, Locale.US).trim();
        String userId = messageSource.getMessage("ftp.userId", null, Locale.US).trim();
        String password = messageSource.getMessage("ftp.password", null, Locale.US).trim();
        remoteDirectory = messageSource.getMessage("ftp.remote.directory", null, Locale.US).trim();
        localDirectory = messageSource.getMessage("ftp.local.directory", null, Locale.US).trim();
        sftpUriRoot = "sftp://" + userId + ":" + password +  "@" + serverAddress + "/" +
                remoteDirectory;
    }
    public boolean sendFile(String fileToFTP) throws FileSystemException {

        try{
            //check if the file exists
            String filepath = localDirectory +  fileToFTP;
            File file = new File(filepath);
            if (!file.exists()) {
                String errorMessage = String.format("Error. Local file %s not found", filepath);
                log.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }

            //Initializes the file manager
            manager.init();

            //Setup our SFTP configuration
            FileSystemOptions opts = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
                    opts, "no");
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
            SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

            // Create local file object
            FileObject localFile = manager.resolveFile(file.getAbsolutePath());

            // Create remote file object
            FileObject remoteFile = manager.resolveFile(sftpUriRoot + fileToFTP, opts);

            // Copy local file to sftp server
            remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
           log.debug(String.format("File %d upload successful", filepath));

        }
        catch (Exception ex) {
            log.error("Error sending file to ftp", ex);
            return false;
        }
        finally {
            manager.close();
        }

        return true;
    }
}
