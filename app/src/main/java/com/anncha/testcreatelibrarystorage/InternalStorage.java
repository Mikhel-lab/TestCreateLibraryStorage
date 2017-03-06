package com.anncha.testcreatelibrarystorage;

import android.os.Environment;
import android.util.Log;

import com.anncha.testcreatelibrarystorage.datamanager.CheckRootDevice;
import com.anncha.testcreatelibrarystorage.datamanager.MyException;
import com.anncha.testcreatelibrarystorage.datamanager.SizeStorage;
import com.anncha.testcreatelibrarystorage.processor.Processable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by AnnCha on 8/9/2016.
 */
public class InternalStorage extends Storage {
    File filePath;
    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReader = null;
    StringBuilder total = null;


    @Override
    boolean createNewFile(String pathFolder, String fileName, Processable processor, Resource content, Callback callback) {

        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());

        String dataInput = content.getData();
        Log.e("aaa","dataInput : " + dataInput);
        String dataWrite = processor.preEnterProcess(dataInput);
        Log.e("aaa","dataWrite : " + dataWrite);

        //dataManager.createNewFile();
        for(int i= 0;i<2;i++){
            try {
                filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
                Log.i("aaa", filePath.getAbsolutePath());
                bufferedWriter = new BufferedWriter(new FileWriter(filePath));
                break;
            } catch (IOException e) {
                e.printStackTrace();
                createFolderDirectionPath(pathFolder);
            }
        }

        //dataManager.writeStringToFile(dataWrite);
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
                bufferedWriter.write(dataWrite);
                Log.i("aaa","file write");

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else throw new MyException("Not Write Data Storage Exception");

        //dataManager.writeClosed();
//        try {
//            bufferedWriter.close();
//            Log.i("aaa","file saved");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    boolean write(String pathFolder, String fileName, Processable processor, Resource content, Callback callback) {

        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());
        String dataInput = content.getData();
        Log.e("aaa","dataInput : " + dataInput);
        String dataWrite = processor.preEnterProcess(dataInput);
        Log.e("aaa","dataWrite : " + dataWrite);

        //dataManager.writeContinueToFile(dataWrite);
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
                FileWriter f = new FileWriter(filePath,true);
                bufferedWriter = new BufferedWriter(f);
                bufferedWriter.write(dataWrite);

                Log.i("aaa","file write continue");

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else throw new MyException("Not Write Data Storage Exception");

        //dataManager.writeClosed();
//        try {
//            bufferedWriter.close();
//            Log.i("aaa","file saved");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    boolean writeClose(Callback callback) {
        try {
            bufferedWriter.close();
            Log.i("aaa","file saved");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    String read(String pathFolder, String fileName, Processable processor, String encode, Callback callback) {

        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());

//        String dataRead = dataManager.readStringFromFile(encode);
        String dataRead;
        String dataOutput = "";
        // example encode = "UTF-8" OR "TIS-620" for Thai language
        if (isExternalStorageReadable()){
            try {
                Log.e("aaa","pathRead : " + filePath);

                FileInputStream inputStream = new FileInputStream(filePath);

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,encode));
                total = new StringBuilder();
                String line;
                int indexStart = 0;

                while ((line = bufferedReader.readLine()) != null) {

                    int indexCut = total.append(line).lastIndexOf("=");
//                    Log.i("aaa","last index of = : " + indexCut);


                    if ((indexCut > 0) && (indexCut > indexStart)) {
                        indexCut = indexCut + 1;
                        dataRead = total.toString().substring(indexStart,indexCut);
                        Log.i("aaa","dataRead : " + dataRead);

//                        if (indexCut > 8023){
//                            dataRead = dataRead + "=";
//                        }

                        dataOutput = dataOutput + processor.postExitProcess(dataRead);
                        Log.i("aaa","dataOutput : " + dataOutput);
                        indexStart = indexStart + indexCut;
                    }
                }

                bufferedReader.close();
                inputStream.close();
                Log.i("aaa", "File contents: " + total);
                Log.i("aaa","total : " + total.toString());
                Log.i("aaa","dataOutput : " + dataOutput);
                Log.i("aaa","file read");

                return dataOutput;

            } catch (FileNotFoundException e) {
                Log.i("aaa","File not found exception : " + e.getMessage());
            } catch (Exception e){
                Log.i("aaa","Exception : " + e.getMessage());
            }
        }else {
            throw new MyException("Not Read Data Storage Exception");
        }
        return null;
    }

    @Override
    boolean clear(String pathFolder, String fileName, Callback callback) {

        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());

        if (filePath != null && fileName != null) {
            Log.i("aaa","Check path clear file : " + filePath.getAbsolutePath());
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("aaa","Clear file " + fileName);
            return true;
        }
        return false;
    }

    @Override
    boolean remove(String pathFolder, String fileName, Callback callback) {
        filePath = new File(Environment.getExternalStorageDirectory() + pathFolder, fileName);
        Log.i("aaa", filePath.getAbsolutePath());

        if (filePath != null && fileName != null) {
            Log.i("aaa","Check path delete file : " + filePath.getAbsolutePath());
            filePath.delete();

            Log.i("aaa","Delete file " + fileName);
            return true;
        }
        return false;
    }

//    @Override
//    boolean write(DataManager dataManager, Processable processor, Resource content, Callback callback) {
//
//        String dataInput = content.getData();
//        String dataWrite = processor.preEnterProcess(dataInput);
//
//        Log.i("aaa","dataInput : " + dataInput);
//        Log.i("aaa","dataWrite : " + dataWrite);
//
//        dataManager.writeContinueToFile(dataWrite);
//        dataManager.writeClosed();
//
//        return true;
//    }
//
//    @Override
//    boolean createNewFile(DataManager dataManager, Processable processor, Resource content, Callback callback) {
//        String dataInput = content.getData();
//        String dataWrite = processor.preEnterProcess(dataInput);
//
//        Log.i("aaa","dataInput : " + dataInput);
//        Log.i("aaa","dataWrite : " + dataWrite);
//
//        dataManager.createNewFile();
//        dataManager.writeStringToFile(dataWrite);
//        dataManager.writeClosed();
//
//        return true;
//    }
//
//    @Override
//    Resource read(DataManager dataManager, Processable processor,String encode, Callback callback) {
//
//        String dataRead = dataManager.readStringFromFile(encode);
//        Log.i("aaa","dataRead : " + dataRead);
//        String dataOutput = processor.postExitProcess(dataRead);
//        Log.i("aaa","dataOutput : " + dataOutput);
//
//        return null;
//    }
//
//    @Override
//    boolean clear(DataManager dataManager, Callback callback) {
//        if (dataManager.clearFile())
//            return true;
//        else return false;
//    }
//
//    @Override
//    boolean remove(DataManager dataManager, Callback callback) {
//        if (dataManager.deleteFile())
//            return true;
//        else return false;
//    }

    private boolean checkSizeStorage() {
        Log.i("aaa","AvailableInternalMemory : " + SizeStorage.getAvailableInternalMemorySize());
        Log.i("aaa","TotalInternalMemorySize : " + SizeStorage.getTotalInternalMemorySize());
        Log.i("aaa","isExternalMemoryAvailable : " + SizeStorage.externalMemoryAvailable());
        Log.i("aaa","AvailablePrimaryExternalMemorySize : " + SizeStorage.getAvailablePrimaryExternalMemorySize());
        Log.i("aaa","TotalPrimaryExternalMemorySize : " + SizeStorage.getTotalPrimaryExternalMemorySize());
        Log.i("aaa","AvailableSecondaryExternalMemorySize : " + SizeStorage.getAvailableSecondaryExternalMemorySize());
        Log.i("aaa","TotalSecondaryExternalMemorySize : " + SizeStorage.getTotalSecondaryExternalMemorySize());

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
