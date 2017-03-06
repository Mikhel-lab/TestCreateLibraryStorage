package com.anncha.testcreatelibrarystorage.processor;

/**
 * Created by non on 8/2/2016.
 */
public interface Processable {

    //void preEnterProcess(int mode ,byte[] data );
    String preEnterProcess(String data);
    String postExitProcess(String data);
    //String getResult();
    //byte[] getByteResult() throws UnsupportedEncodingException;
}
