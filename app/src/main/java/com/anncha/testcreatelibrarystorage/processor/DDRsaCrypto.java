package com.anncha.testcreatelibrarystorage.processor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by non on 8/3/2016.
 */
public class DDRsaCrypto {

    KeyPairGenerator kpg;
    KeyPair kp;
    PublicKey publicKey;
    PrivateKey privateKey;
    String fixPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJ893X3wKCHhbhBguXFrb94d0n2YrLR6uhZSasj4wSdzrNoC5QbhM3lcBKagiYdpDRte8yci4YsSKpJxvlxF8/i6ucJbJ0YKoukgJW8bx22CMuQ3mjQs1o389cTpEWCCxDQShmIsdps8bw6ossXtxr9zG7hoidEjowVF5kzxuCxwIDAQAB";
    String fixPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMnz3dffAoIeFuEGC5cWtv3h3SfZistHq6FlJqyPjBJ3Os2gLlBuEzeVwEpqCJh2kNG17zJyLhixIqknG+XEXz+Lq5wlsnRgqi6SAlbxvHbYIy5DeaNCzWjfz1xOkRYILENBKGYix2mzxvDqiyxe3Gv3MbuGiJ0SOjBUXmTPG4LHAgMBAAECgYASUDoC1GdO01mxyDGO3vFzLmM2tOSAMJygwyJxrYa/Oe6sLOVmR8UYWk0YsmrbII1bZwss4OvLM2z4N+KwwFD+scQLW1qhaWUYB3DwTj7Kfr1W1AhqeKkxTwBsmSo1wkEqBy4gjRT0q7sam1sKZ6hDSTM87+QSpL//RauAYayI6QJBAPsEUh1y9n4k3OJL7EjO7h8TjFu/vian/UYREQ0rf9g9+CTGzg3/iV9fBsMlnxTABU8QPEqLpSSAwHo4pB3HDcMCQQDN9jLtJ2Eb6031dC1E0Lo8wE4mTg3mRdCP4/WjZxj9GsdTrxliz/w2m2s7hz9g04TgR3vpdE8JIte48ho9apKtAkEAxwi1B67JblpSXoku8MhLnY6Fg5Z08vfO5sjPiW7Nbywr3xVX+1d8mIejuAbTrcNYMknAVKeHqXsBo6fX0disuwJAShKriiNJh5L+/LIFveQbttGqyZf048ZkFfv0Ugo4MH87BNvKk4Edwj3vOP+RSa+I8GiFJhcQfcasMvXPfhsgGQJAPshW/rmYxENVS/sY8dSJiL1UCgCV/0Ndd4eX2jfmUXle6Y2cnh19bGZ8zqeGH3CPAbMeoTwYNQlwOORdDdEHFw==";
    byte[] encryptedBytes;
    byte[] decryptedBytes;
    Cipher cipher;
    String encrypted, decrypted;

    public DDRsaCrypto() {

    }

    public void generateKeyRSA(Context context) {
        String startKey = "password";
        byte[] keyStart = new byte[0];
        try {
            keyStart = startKey.getBytes("UTF-8");
            kpg = KeyPairGenerator.getInstance("RSA");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(keyStart);
            kpg.initialize(1024);
            kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
            byte[] publicKeyBytes = kp.getPublic().getEncoded();
            byte[] privateKeyBytes = kp.getPrivate().getEncoded();

            String pubKeyStr = new String(Base64.encode(publicKeyBytes, Base64.DEFAULT));
            String priKeyStr = new String(Base64.encode(privateKeyBytes, Base64.DEFAULT));
            Log.i("RSA", "public key byte = " + publicKeyBytes.toString());
            Log.i("RSA", "private key byte  = " + privateKeyBytes.toString());
            Log.i("RSA", "public key  = " + pubKeyStr);
            Log.i("RSA", "private key = " + priKeyStr);

            SharedPreferences sharedPref = context.getSharedPreferences("keystore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("publickey", pubKeyStr);
            editor.commit();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getFixPublicKey() {
        return fixPublicKey;
    }

    public String getFixPrivateKey() {
        return fixPrivateKey;
    }

    public PrivateKey getPrivateKey() {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(getFixPrivateKey().getBytes(), Base64.DEFAULT));
            KeyFactory kf = null;
            kf = KeyFactory.getInstance("RSA");
            //Log.i("RSA", "result private = " + kf.generatePrivate(spec).getEncoded().toString());
            //Log.i("RSA", "result private = " + fixPrivateKey);
            //Log.i("RSA", "result private = " + new String(Base64.encode(kf.generatePrivate(spec).getEncoded(), Base64.DEFAULT)));
            return kf.generatePrivate(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PublicKey getPublicKey() {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(getFixPublicKey().getBytes(), Base64.DEFAULT));
            KeyFactory kf = null;
            kf = KeyFactory.getInstance("RSA");
            //Log.i("RSA", "result public = " + kf.generatePublic(spec).getEncoded().toString());
            //Log.i("RSA", "result public = " + fixPublicKey);
            //Log.i("RSA", "result public = " + new String(Base64.encode(kf.generatePublic(spec).getEncoded(), Base64.DEFAULT)));
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptedRSA(String data) {
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            Log.i("RSA", "cipher block size = " + cipher.getBlockSize());
            byte[] byteData = data.trim().getBytes("UTF-8");
            //encryptedBytes = cipher.doFinal(byteData);//blockCipher(byteData,Cipher.ENCRYPT_MODE);//cipher.doFinal(data.trim().getBytes(Charset.forName("UTF-8")));
            encryptedBytes = blockCipher(byteData, Cipher.ENCRYPT_MODE);//cipher.doFinal(data.trim().getBytes(Charset.forName("UTF-8")));
            encrypted = new String(Base64.encode(encryptedBytes, Base64.DEFAULT));

//            encrypted = new String((encryptedBytes));

            //Log.i("RSA", "Encrypted Result = " + encrypted);
            //Log.i("RSA", "Encrypted byte = " + encryptedBytes);
            Log.i("RSA", "Encrypted Result = " + encrypted);
            return encrypted;

        } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String decryptedRSA(String data) {
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            Log.i("RSA", "cipher block size decrypt = " + cipher.getBlockSize());
            byte[] byteData = Base64.decode(data.trim().getBytes("UTF-8"), Base64.DEFAULT);

//            byte[] byteData = data.trim().getBytes("UTF-8");

            //decryptedBytes = cipher.doFinal(byteData);//blockCipher(byteData,Cipher.DECRYPT_MODE); ;//cipher1.doFinal(encryptedBytes);//data.getBytes(Charset.forName("UTF-8")));
            decryptedBytes = blockCipher(byteData, Cipher.DECRYPT_MODE);//cipher1.doFinal(encryptedBytes);//data.getBytes(Charset.forName("UTF-8")));
            decrypted = new String(decryptedBytes);
            Log.i("RSA", "DDecrypted Result = " + decrypted.trim());
            return decrypted.trim();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void signatureVerify(String signature, String original, String publicKey) {
        /*********************************
         * Document for select algorithm
         * https://developer.android.com/reference/java/security/Signature.html
         * *******************************/
        try {
            PublicKey pk = getPublicKey();
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(getPrivateKey());
            sig.update(original.getBytes("UTF8"));
            sig.verify(signature.getBytes("UTF8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private byte[] blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException {
        // string initialize 2 buffers.
        // scrambled will hold intermediate results
        byte[] scrambled;

        // toReturn will hold the total result
        byte[] toReturn = new byte[0];
        // if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
        //int length = (mode == Cipher.ENCRYPT_MODE) ? cipher.getBlockSize() : cipher.getBlockSize();
        int length =  cipher.getBlockSize();

        // another buffer. this one will hold the bytes that have to be modified in this step
        byte[] buffer = new byte[length];

        for (int i = 0; i < bytes.length; i++) {

            // if we filled our buffer array we have our block ready for de- or encryption
            if ((i > 0) && (i % length == 0)) {
                scrambled = cipher.doFinal(buffer);
                toReturn = append(toReturn, scrambled);
                // here we calculate the length of the next buffer required
                int newlength = length;

                // if newlength would be longer than remaining bytes in the bytes array we shorten it.
                if (i + length > bytes.length) {
                    newlength = bytes.length - i;
                }
                // clean the buffer array
                buffer = new byte[newlength];
            }
            // copy byte into our buffer.
            buffer[i % length] = bytes[i];
        }

        // this step is needed if we had a trailing buffer. should only happen when encrypting.
        // example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
        Log.i("RSA","Block length = " + buffer.length  + "/" + bytes.length );
        scrambled = cipher.doFinal(buffer);

        // final step before we can return the modified data.
        toReturn = append(toReturn, scrambled);

        return toReturn;
    }

    private byte[] append(byte[] prefix, byte[] suffix) {
        byte[] toReturn = new byte[prefix.length + suffix.length];
        for (int i = 0; i < prefix.length; i++) {
            toReturn[i] = prefix[i];
        }
        for (int i = 0; i < suffix.length; i++) {
            toReturn[i + prefix.length] = suffix[i];
        }
        return toReturn;
    }
}
