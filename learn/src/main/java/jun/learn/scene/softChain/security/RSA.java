package jun.learn.scene.softChain.security;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/**
 * rsa encrypt and signer SHA1 with PKCS#1 v1.5 padding.
 */
public class RSA
{
    public static void dump(String label, byte[] data)
    {
        String hex_str = Base16.encode(data);
        System.out.println(label + "=" + hex_str);
    }
    
    private Cipher enc; // encryptor.
    private Cipher dec; // decryptor.
    private Key key; // the enc/dec key.
    private int KEY_BYTE_LEN; // RSA key bytes length.
    
    public RSA()
    {
    }
    
    /**
     * init public key to encrypt/decrypt, all operations use this key.
     * @param N N factor in RSA, aslo called modulus.
     * @param e e factor in RSA, aslo called publicExponent.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public void initPublicKey(String N, String e) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException
    {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        BigInteger big_N = new BigInteger(N);
        KEY_BYTE_LEN = (big_N.bitLength()) >> 3;
        BigInteger big_e = new BigInteger(e);
        KeySpec keySpec = new RSAPublicKeySpec(big_N, big_e);
        key = keyFactory.generatePublic(keySpec);
    }
    
    /**
     * init private key to encrypt/decrypt, all operations use this key.
     * @param N N factor in RSA, aslo called modulus.
     * @param e e factor in RSA, aslo called publicExponent, ignored, just keep compatible with C++ interface.
     * @param d d factor in RSA, aslo called privateExponent.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    public void initPrivateKey(String N, String e, String d) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException
    {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        BigInteger big_N = new BigInteger(N);
        KEY_BYTE_LEN = (big_N.bitLength()) >> 3;
        BigInteger big_d = new BigInteger(d);
        KeySpec keySpec = new RSAPrivateKeySpec(big_N, big_d);
        key = keyFactory.generatePrivate(keySpec);
    }
    
    /**
     * get maxim plain bytes length that RSA can encrypt.
     * @return the maxim length.
     */
    public int getMaxPlainLen()
    {
        return KEY_BYTE_LEN - 11;
    }
    
    /**
     * get cipher length that return by RSA encryption.
     * in RSA, this length is fixed, and equals the key bytes length - 11.
     * e.g. 1024 bits RSA key, this value is 128-11 = 117.
     * @return the cipher length.
     */
    public int getCipherLen()
    {
        return KEY_BYTE_LEN;
    }
    
    /**
     * encrypt indata to outdata use the key.
     * @param indata input data.
     * @param inoff input data offset.
     * @param inlen input data length.
     * @param outdata output data.
     * @param outoff output data offset.
     * @return the actual cipher length.
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public int encrypt(byte[] indata, int inoff, int inlen, byte[] outdata, int outoff)
            throws ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        initEncryptor();
        return enc.doFinal(indata, inoff, inlen, outdata, outoff);
    }
    
    /**
     * encrypt indata to outdata use the key.
     * @param indata input data.
     * @param inoff input data offset.
     * @param inlen input data length.
     * @return the actual cipher data.
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public byte[] encrypt(byte[] indata, int inoff, int inlen) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        initEncryptor();
        return enc.doFinal(indata, inoff, inlen);
    }
    
    /**
     * encrypt indata to outdata use the key.
     * @param indata input data.
     * @return the actual cipher data.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encrypt(byte[] indata) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        initEncryptor();
        return enc.doFinal(indata);
    }
    
    /**
     * get the maxim plain data length after decryption.
     * the actual plain data length may be shorter than this value.
     * @param len the cipher data length.
     * @return the maxim plain data length.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public int getPlainLen(int len) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        initDecryptor();
        return dec.getOutputSize(len);
    }
    
    /**
     * decrypt input data to output data.
     * @param indata input data.
     * @param inoff input data offset.
     * @param inlen input data length.
     * @param outdata output data.
     * @param outoff output data offset.
     * @return the actual plain length.
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public int decrypt(byte[] indata, int inoff, int inlen, byte[] outdata, int outoff)
            throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        initDecryptor();
        return dec.doFinal(indata, inoff, inlen, outdata, outoff);
    }
    
    /**
     * decrypt input data to output data.
     * @param indata input data.
     * @param inoff input data offset.
     * @param inlen input data length.
     * @return the actual plain data.
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public byte[] decrypt(byte[] indata, int inoff, int inlen) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        initDecryptor();
        return dec.doFinal(indata, inoff, inlen);
    }
    
    /**
     * decrypt input data to output data.
     * @param indata input data.
     * @return the actual plain data.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decrypt(byte[] indata) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        initDecryptor();
        return dec.doFinal(indata);
    }
    
    private void initEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        if (null == enc)
        {
            enc = Cipher.getInstance("RSA");
            enc.init(Cipher.ENCRYPT_MODE, key);
        }
    }
    
    private void initDecryptor() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        if (null == dec)
        {
            dec = Cipher.getInstance("RSA");
            dec.init(Cipher.DECRYPT_MODE, key);
        }
    }
}
