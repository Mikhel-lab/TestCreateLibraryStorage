package com.anncha.testcreatelibrarystorage.datamanager;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by AnnCha on 8/3/2016.
 */
public class DataManager {

    String pathFolder;
    String fileName;
    File filePath;
    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReader = null;
    StringBuilder total = null;

    public DataManager(String pathFolder, String fileName) throws IOException {

        this.pathFolder = pathFolder;
        this.fileName = fileName;

//        for(int i= 0;i<2;i++){
//            try {
//                path = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
//                Log.i("aaa", path.getAbsolutePath());
//                bufferedWriter = new BufferedWriter(new FileWriter(path));
//                break;
//            } catch (IOException e) {
//                e.printStackTrace();
//                createFolderDirectionPath(pathFolder);
//            }
//        }

        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());

    }

    public boolean createNewFile(){
        for(int i= 0;i<2;i++){
            try {
                filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
                Log.i("aaa", filePath.getAbsolutePath());
                bufferedWriter = new BufferedWriter(new FileWriter(filePath));
                break;
            } catch (IOException e) {
                e.printStackTrace();
                createFolderDirectionPath(pathFolder);
                return false;
            }
        }
        return true;
    }

    public void writeStringToFile(String data) {

        if(!checkSizeStorage()){
            //error
        }
        CheckRootDevice checkRootDevice = new CheckRootDevice();
        Log.i("aaa","Check Root Device (true is root) : " + checkRootDevice.isRooted());
        if (checkRootDevice.isRooted()){
            // true is root
            throw new MyException("Root Device Exception");
        }

        if (isExternalStorageWritable()){
            createFolderDirectionPath(pathFolder);
            try {
                bufferedWriter.write(data);
                Log.i("aaa","file write");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else throw new MyException("Not Write Data Storage Exception");
    }

    public void writeContinueToFile(String data){
        if(!checkSizeStorage()){
            //error
        }
        CheckRootDevice checkRootDevice = new CheckRootDevice();
        Log.i("aaa","Check Root Device (true is root) : " + checkRootDevice.isRooted());
        if (checkRootDevice.isRooted()){
            // true is root
            throw new MyException("Root Device Exception");
        }

        if (isExternalStorageWritable() && filePath.exists()){
//            createFolderDirectionPath(pathFolder);
            try {
                FileWriter f_writer = new FileWriter(filePath,true);
                bufferedWriter = new BufferedWriter(f_writer);
                bufferedWriter.write(data);

                Log.i("aaa","file write continue");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else throw new MyException("Not Write Data Storage Exception");
    }

    public void writeClosed(){
        try {
            bufferedWriter.close();
            Log.i("aaa","file saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readStringFromFile(String encode) {
        // example encode = "UTF-8" OR "TIS-620" for Thai language

        if (isExternalStorageReadable()){
            try {
//                File pathRead = new File(Environment.getExternalStorageDirectory()+ pathFolder,fileName);
                Log.e("aaa","pathRead : " + filePath);

                FileInputStream inputStream = new FileInputStream(filePath);

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,encode));
                total = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    total.append(line).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                Log.i("aaa", "File contents: " + total);
                Log.i("aaa","file read");
                return total.toString();

            } catch (FileNotFoundException e) {
                Log.i("aaa","File not found exception : " + e.getMessage());
            } catch (Exception e){
                Log.i("aaa","Exception : " + e.getMessage());
            }
        }else {}throw new MyException("Not Read Data Storage Exception");

    }

    public boolean clearFile(){

        if (filePath != null && fileName != null) {
            Log.i("aaa","Check path clear file : " + filePath.getAbsolutePath());
            try {
                BufferedWriter bufferedWclear;
                bufferedWclear = new BufferedWriter(new FileWriter(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("aaa","Clear file " + fileName);
            return true;
        }
        return false;
    }

    public boolean deleteFile() {

        if (filePath != null && fileName != null) {
            Log.i("aaa","Check path delete file : " + filePath.getAbsolutePath());
            filePath.delete();

            Log.i("aaa","Delete file " + fileName);
            return true;
        }
        return false;
    }

    private boolean checkSizeStorage() {
        SizeStorage managerStorage = new SizeStorage();
        Log.i("aaa","TotalInternalMemorySize : " + managerStorage.getTotalInternalMemorySize());
        Log.i("aaa","AvailableInternalMemory" + managerStorage.getAvailableInternalMemorySize());
        return true;
    }

    private boolean createFolderDirectionPath (String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.i("aaa", "Problem creating folder");
                ret = false;
            }
            Log.i("aaa","Folder create");
        }
        return ret;
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}