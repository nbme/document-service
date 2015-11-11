package org.nbme.wbti.document;

import junit.framework.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by RWang on 11/2/2015.
 */
public class SHA1Compare {
    private static final int CHUNK_SIZE = 4096;

    public static void assertEqualsSHA1(String expectedPath, String actualPath) throws IOException, NoSuchAlgorithmException {
        File expectedFile = new File(expectedPath);
        File actualFile = new File(actualPath);
        Assert.assertEquals(expectedFile.length(), actualFile.length());
        try (InputStream fisExpected = new FileInputStream(actualFile);
             FileInputStream fisActual = new FileInputStream(expectedFile)) {
            Assert.assertEquals(makeMessageDigest(fisExpected),
                    makeMessageDigest(fisActual));
        }
    }

    private static String makeMessageDigest(InputStream is) throws NoSuchAlgorithmException, IOException {
        byte[] data = new byte[CHUNK_SIZE];
        MessageDigest md = MessageDigest.getInstance("SHA1");
        int bytesRead = 0;
        while(-1 != (bytesRead = is.read(data, 0, CHUNK_SIZE))) {
            md.update(data, 0, bytesRead);
        }
        return toHexString(md.digest());
    }

    private static String toHexString(byte[] digest) {
        StringBuilder sha1HexString = new StringBuilder();
        for(int i = 0; i < digest.length; i++) {
            sha1HexString.append(String.format("%1$02x", Byte.valueOf(digest[i])));
        }
        return sha1HexString.toString();
    }
}
