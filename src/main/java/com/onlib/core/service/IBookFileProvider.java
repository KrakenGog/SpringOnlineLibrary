package com.onlib.core.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public interface IBookFileProvider {
    
    public abstract void saveEpubFile(byte[] epub, Long id) throws IOException;

    public abstract byte[] getEpubFile(Long id) throws IOException;
}
