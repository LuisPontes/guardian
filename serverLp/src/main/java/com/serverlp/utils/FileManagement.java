/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serverlp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author devel
 */
public class FileManagement {

//String directory = System.getProperty("user.home");
//String fileName = "sample.txt";
//String absolutePath = directory + File.separator + fileName;
    
    public static boolean fileExist(String fileName) {
        return (new File(fileName).exists());
    }

//    public static boolean createFile(String fileName, String ip) {
//        File file;
//        try {
//            //file name only
//            file = new File(fileName);
//            if (file.createNewFile()) {
//                System.out.println("file.txt File Created in Project root directory");
//                return true;
//            } else {
//                System.out.println("File file.txt already exists in the project root directory");
//                return false;
//            }
//        } catch (Exception e) {
//            System.out.println("com.serverlp.services.guardion.GuardionService.createfile()" + e);
//            return false;
//        }
//    }

    public static File getFile(String fileName) {
        return (new File(fileName));
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static boolean writeFile(String fileName,String ip ) {
        // Write the content in file 
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String fileContent = ip;
            fileWriter.write(fileContent);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            // Cxception handling
            return false;
        }
    }
    
    public static String readFile(String fileName) {
        //// Read the content from file
        String ip = "";
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferreader.readLine()) != null) {
//                System.out.println(line);
                ip = line;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ip;
    }

//    public static void main(String[] args) {
//        writeFile(null, "test.txt");
//        readFile("test.txt");
//    }
}
