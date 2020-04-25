package com.umdvita.taxtracker.backend.service.security;

import java.io.UnsupportedEncodingException;

/**
 * This is the contract for the EncryptionService operations.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface EncryptionService {

  /**
   * Generates a username and password in encoded form.
   *
   * @param username the username
   * @param password the password
   * @return the encoded string
   */
  String base64Encode(String username, String password);

  /**
   * Encrypts the string provided using BCrypt.
   *
   * @param plainText the text
   * @return the encrypted text
   */
  String encryptText(String plainText);

  /**
   * Checks if the text matches the encrypted sequence.
   *
   * @param plainText    the text
   * @param encryptedKey the encrypted text
   * @return the encrypted text
   */
  boolean checkMatch(String plainText, String encryptedKey);

  /**
   * Encrypts the uri to be sent out.
   *
   * @param uri the uri
   * @return the encrypted uri
   */
  String encryptRequestUri(String uri);

  /**
   * Decrypts the encryptedUri to be sent out.
   *
   * @param encryptedUri the encryptedUri
   * @return the decrypted uri
   */
  String decryptRequestUri(String encryptedUri);

  /**
   * Encode url before appending in link.
   *
   * @param url the url
   * @return the encoded url
   * @throws UnsupportedEncodingException if the text is not supported
   */
  String encodeUrl(String url) throws UnsupportedEncodingException;

  /**
   * Decode url before appending in link.
   *
   * @param url the url
   * @return the encoded url
   * @throws UnsupportedEncodingException if the text is not supported
   */
  String decodeUrl(String url) throws UnsupportedEncodingException;

  /**
   * Encode text before appending in link.
   *
   * @param text the text
   * @return the encoded text
   */
  String encodeText(String text);

  /**
   * Decode text before appending in link.
   *
   * @param text the text
   * @return the encoded text
   */
  String decodeText(String text);

}
