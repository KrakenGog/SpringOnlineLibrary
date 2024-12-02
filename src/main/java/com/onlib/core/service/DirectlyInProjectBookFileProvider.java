package com.onlib.core.service;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DirectlyInProjectBookFileProvider implements IBookFileProvider {

    private String savingPath;

    public DirectlyInProjectBookFileProvider(String savingPath) {
        this.savingPath = savingPath;
    }

    @Override
    public void saveEpubFile(byte[] epub, Long id) throws IOException {

        try (OutputStream stream = new FileOutputStream(savingPath + "/" + id + ".epub")) {
            stream.write(epub);
        } catch (IOException ex) {
            throw ex;
        }
    }

    @Override
    public byte[] getEpubFile(Long id) throws IOException {
        File file = new File(savingPath);
        File[] matchingFiles = file.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.equals(id.toString() + ".epub");
            }
        });

        if (matchingFiles.length > 1)
            throw new IOException("Two or more epub files for id: " + id);

        if (matchingFiles.length == 0)
            throw new IOException("No file founded for id: " + id);

        InputStream stream = new FileInputStream(matchingFiles[0]);
        byte[] bytes = stream.readAllBytes();
        stream.close();
        return bytes;
    }

}
