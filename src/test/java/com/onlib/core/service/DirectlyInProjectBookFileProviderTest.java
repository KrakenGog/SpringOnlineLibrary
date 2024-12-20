package com.onlib.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DirectlyInProjectBookFileProviderTest {

    @Test
    public void saveEpubFile_readEpubFile() {
        DirectlyInProjectBookFileProvider provider = new DirectlyInProjectBookFileProvider("./");
        byte[] data = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };

        try {
            provider.saveEpubFile(data, 1L);
        } catch (IOException ex) {
            fail("Method saveEpubFile cant throw exception in this context");
        }

        File file = new File("./1.epub");
        assertTrue(file.exists() && !file.isDirectory());

        byte[] result = null;
        try {
            result = provider.getEpubFile(1L);
        } catch (IOException ex) {
            fail("Method getEpubFile cant throw exception in this context");
        }

        assertTrue(Arrays.equals(result, data));
        try {
            Files.delete(Path.of("./1.epub"));
        } catch (IOException ex) {
            fail("Unexpectable exception");
        }
    }
}
