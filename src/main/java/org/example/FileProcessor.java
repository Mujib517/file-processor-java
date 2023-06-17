package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
    public static Map<String, Integer> divideIntoChunks() {
        Map<String, Integer> map = new HashMap<>();
        String sourceFilePath = "image.jpeg";
        String destinationFolderPath = "chunks/";
        int chunkSizeInBytes = 1024 * 50; // 50KB chunk size
        try {
            InputStream inputStream = FileProcessor.class.getClassLoader().getResourceAsStream(sourceFilePath);
            File destinationFolder = new File(destinationFolderPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            byte[] buffer = new byte[chunkSizeInBytes];
            int bytesRead;
            int chunkNumber = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String filename = "chunk_" + chunkNumber + ".ext";
                String chunkFilePath = destinationFolderPath + filename;
                map.put(filename, chunkNumber);
                File chunkFile = new File(chunkFilePath);
                FileOutputStream fos = new FileOutputStream(chunkFile);
                fos.write(buffer, 0, bytesRead);
                fos.close();
                chunkNumber++;
            }
            inputStream.close();
            System.out.println("File successfully divided into " + chunkNumber + " chunks.");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
