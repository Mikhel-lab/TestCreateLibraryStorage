package com.anncha.testcreatelibrarystorage.processor;


import android.util.Log;

/**
 * Created by non on 8/3/2016.
 */
public class RSA implements Processable {

    @Override
    public String preEnterProcess(String data) {
        DDRsaCrypto rsa = new DDRsaCrypto();
        return rsa.encryptedRSA(data);
    }

    @Override
    public String postExitProcess(String data) {
        DDRsaCrypto rsa = new DDRsaCrypto();
        Log.e("aaa","postExitProcess : " + rsa.decryptedRSA(data));
        return rsa.decryptedRSA(data);
    }

    public void encrypt(String data) {
        DDRsaCrypto rsa = new DDRsaCrypto();
        rsa.encryptedRSA(new String(data));
    }

    /*
    public void encrypt(byte[] data) {
        DDRsaCrypto rsa = new DDRsaCrypto();

        if (mode == Cipher.ENCRYPT_MODE) {
            result = rsa.encryptedRSA(new String(data));
        } else if (mode == Cipher.DECRYPT_MODE) {
            result = rsa.decryptedRSA(new String(data));
        }

    }*/


    /*public void decrypt(String data) {
        DDRsaCrypto rsa = new DDRsaCrypto();
        rsa.decryptedRSA(new String(data));
    }

    public String getResult() {
        return result;
    }


    public byte[] getByteResult() throws UnsupportedEncodingException {
        return byteResult = result.getBytes("UTF-8");
    }
*/

}
