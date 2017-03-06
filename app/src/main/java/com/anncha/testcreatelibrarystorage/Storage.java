package com.anncha.testcreatelibrarystorage;

import com.anncha.testcreatelibrarystorage.processor.Processable;

/**
 * Created by AnnCha on 8/9/2016.
 */
abstract class Storage {
    enum Action {CREATENEWFILE, WRITE, WRITECLOSE, READ, CLEAR, REMOVE};

    //createNewFile กับ write ทุกครั้งที่เรียกใช้ต้องปิดด้วย writeClose เสมอ
    //createNewFile สร้างไฟล์ขึ้นมาใหม่พร้อมเขียนลงไปในไฟล์
    abstract boolean createNewFile(String pathFolder, String fileName, Processable processor, Resource content, Callback callback);
    //write เป็นการเปิดไฟล์ที่มีอยู่แล้วขึ้นมาเขียนต่อ
    abstract boolean write(String pathFolder, String fileName, Processable processor, Resource content, Callback callback);
    abstract boolean writeClose(Callback callback);
    abstract String read(String pathFolder, String fileName, Processable processor, String encode, Callback callback);
    abstract boolean clear(String pathFolder, String fileName, Callback callback);
    abstract boolean remove(String pathFolder, String fileName, Callback callback);

    public static interface Callback{
        public void onTaskDone(Action action, Resource r);
    }

//    public Processable getProcessable(){};
//    public DataManager getDataManager(){};
//    public Resource getResource(){};


//    public static interface DataManager{
//        //create path
//    }

//    public static interface Resource implements Iterator<String>{
//        boolean hasNext();
//        String next();
//        void remove();
//        String toString();
//        int getVersion();
//    }


}
