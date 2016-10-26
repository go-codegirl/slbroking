package com.telosws.broking.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Harish Kalepu
 *
 */
public class Encryptor {
    public static String encrypt(String key, String initVector, String value) {
        try {
        	if(key == null || key.trim().length() == 0)
        	{
        		key = Encryptor.key;
        	}
        	if(initVector == null || initVector.trim().length() == 0)
        	{
        		initVector = Encryptor.initVector;
        	}
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
        	if(key == null || key.trim().length() == 0)
        	{
        		key = Encryptor.key;
        	}
        	if(initVector == null || initVector.trim().length() == 0)
        	{
        		initVector = Encryptor.initVector;
        	}
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String key = "BhArat2016telO09"; // 128 bit key
    private static String initVector = "TelowsInitVector"; // 16 bytes IV

}