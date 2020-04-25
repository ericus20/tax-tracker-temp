package com.umdvita.taxtracker.backend.service.security.impl;

import com.umdvita.taxtracker.backend.service.security.EncryptionService;
import com.umdvita.taxtracker.config.core.ApplicationProperties;
import com.umdvita.taxtracker.exception.tax.TaxTrackerException;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The EncryptionServiceImpl class provides implementation for the EncryptionService definitions.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

  private static final String ERROR_ENCRYPTING_DATA = "Error encrypting data";
  private static final String ERROR_DECRYPTING_DATA = "Error decrypting data";
  private static final String AES = "AES";
  private static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5PADDING";

  private final PasswordEncoder passwordEncoder;
  private final ApplicationProperties properties;

  @Autowired
  public EncryptionServiceImpl(PasswordEncoder passwordEncoder, ApplicationProperties properties) {
    this.passwordEncoder = passwordEncoder;
    this.properties = properties;
  }

  @Override
  public String base64Encode(String username, String password) {
    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
    return "Basic " + new String(encodedAuth);
  }

  /**
   * Encrypts the string provided using BCrypt.
   *
   * @param plainText the text
   * @return the encrypted text
   */
  @Override
  public String encryptText(String plainText) {
    return passwordEncoder.encode(plainText);
  }

  /**
   * Checks if the text matches the encrypted sequence.
   *
   * @param plainText    the text
   * @param encryptedKey the encrypted text
   * @return the encrypted text
   */
  @Override
  public boolean checkMatch(String plainText, String encryptedKey) {
    return passwordEncoder.matches(plainText, encryptedKey);
  }

  /**
   * Encrypts the uriToEncrypt to be sent out.
   *
   * @param uriToEncrypt the uriToEncrypt
   * @return the encrypted uriToEncrypt
   */
  @Override
  public String encryptRequestUri(String uriToEncrypt) {
    LOG.debug("About to encrypt uri {}", uriToEncrypt);
    try {
      InputValidationUtility.validateInputs(getClass(), uriToEncrypt);
      String encrypted = encrypt(uriToEncrypt);
      LOG.debug("Uri encrypted as {}", encrypted);

      String encodeUrl = encodeUrl(encrypted);
      LOG.debug("Uri encoded as {}", encodeUrl);
      return encodeUrl;
    } catch (Exception ex) {
      LOG.debug(ERROR_ENCRYPTING_DATA, ex);
      throw new TaxTrackerException(ex);
    }
  }

  /**
   * Decrypts the encryptedUri to be sent out.
   *
   * @param encryptedUri the encryptedUri
   * @return the decrypted uri
   */
  @Override
  public String decryptRequestUri(String encryptedUri) {
    LOG.debug("About to decrypt uri {}", encryptedUri);
    try {
      InputValidationUtility.validateInputs(getClass(), encryptedUri);
      String decodeUrl = decodeUrl(encryptedUri);
      LOG.debug("Uri decoded as {}", decodeUrl);

      String decrypt = decrypt(decodeUrl);
      LOG.debug("Uri decrypted as {}", decrypt);
      return decrypt;
    } catch (Exception ex) {
      LOG.debug(ERROR_DECRYPTING_DATA, ex);
      throw new TaxTrackerException(ex);
    }
  }

  /**
   * Encode url before appending in link.
   *
   * @param url the url
   * @return the encoded url
   */
  @Override
  public String encodeUrl(String url) throws UnsupportedEncodingException {
    return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
  }

  /**
   * Decode url before appending in link.
   * The plus sign "+" is converted into a space character " " so we have undo this change to have '+' back.
   *
   * @param url the url
   * @return the encoded url
   */
  @Override
  public String decodeUrl(String url) throws UnsupportedEncodingException {
    return URLDecoder.decode(url, StandardCharsets.UTF_8.toString()).replaceAll("\\s", "+");
  }

  /**
   * Encode path before appending in link.
   *
   * @param text the path
   * @return the encoded path
   */
  @Override
  public String encodeText(String text) {
    return encrypt(text);
  }

  /**
   * Decode path before appending in link.
   *
   * @param text the path
   * @return the encoded path
   */
  @Override
  public String decodeText(String text) {
    return decrypt(text);
  }

  private String encrypt(String text) {
    try {
      IvParameterSpec iv = new IvParameterSpec(properties.getEncryptionIntVec().getBytes(StandardCharsets.UTF_8));
      SecretKeySpec keySpec = new SecretKeySpec(properties.getEncryptionKey().getBytes(StandardCharsets.UTF_8), AES);
      Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
      byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
      return Base64.encodeBase64String(encrypted);
    } catch (Exception ex) {
      LOG.debug(ERROR_ENCRYPTING_DATA, ex);
      throw new TaxTrackerException(ex);
    }
  }

  private String decrypt(String text) {
    try {
      IvParameterSpec iv = new IvParameterSpec(properties.getEncryptionIntVec().getBytes(StandardCharsets.UTF_8));
      SecretKeySpec skeySpec = new SecretKeySpec(properties.getEncryptionKey().getBytes(StandardCharsets.UTF_8), AES);

      Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      byte[] original = cipher.doFinal(Base64.decodeBase64(text));

      return new String(original);
    } catch (Exception ex) {
      LOG.debug(ERROR_DECRYPTING_DATA, ex);
      throw new TaxTrackerException(ex);
    }
  }
}
