package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class FileAggregator {
    public static void build(Map<String, Integer> chunksMeta) {
        String chunksFolderPath = "chunks/";
        String outputFilePath = "output/image.jpeg";

        try {
            File destinationFolder = new File("output");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            File outputFile = new File(outputFilePath);
            FileOutputStream fos = new FileOutputStream(outputFile);

            File chunksFolder = new File(chunksFolderPath);
            File[] chunkFiles = chunksFolder.listFiles();

            if (chunkFiles != null) {
                sortChunkFiles(chunkFiles, chunksMeta);
                for (File chunkFile : chunkFiles) {
                    FileInputStream fis = new FileInputStream(chunkFile);
                    byte[] buffer = new byte[(int) chunkFile.length()];
                    fis.read(buffer);
                    fis.close();
                    fos.write(buffer);
                }
            }
            fos.close();
            System.out.println("Chunks successfully aggregated into the output file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sortChunkFiles(File[] chunkFiles, Map<String, Integer> chunksMeta) {
        Arrays.sort(chunkFiles, (f1, f2) -> {
            int chunkNumber1 = chunksMeta.get(f1.getName());
            int chunkNumber2 = chunksMeta.get(f2.getName());
            return Integer.compare(chunkNumber1, chunkNumber2);
        });
    }
}
