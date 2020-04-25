package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.exception.tax.TaxTrackerException;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles(value = {ProfileType.TEST})
class EncryptionServiceTest {

  @Autowired
  protected EncryptionService encryptionService;

  @Test
  void testEncryptionAndDecryptionOfUri() {
    String uri = "https://server.ericopoku.com/mslc-vita/user/forgot/change";
    String encryptRequestUri = encryptionService.encodeText(uri);
    Assertions.assertNotNull(encryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, uri);

    String decryptRequestUri = encryptionService.decodeText(encryptRequestUri);
    Assertions.assertNotNull(decryptRequestUri);
    Assertions.assertNotEquals(decryptRequestUri, encryptRequestUri);

    Assertions.assertEquals(uri, decryptRequestUri);
  }

  @Test
  void encodeAndDecodeLast4ssn() {
    String ssn = UserUtility.generateSsn();
    String last4ssn = UserUtility.getLast4Ssn(ssn);
    String encodeText = encryptionService.encodeText(last4ssn);
    Assertions.assertNotEquals(last4ssn, encodeText);

    String decodeText = encryptionService.decodeText(encodeText);
    Assertions.assertNotEquals(encodeText, decodeText);
    Assertions.assertEquals(last4ssn, decodeText);
  }

  @Test
  void testEncryptTextShouldThrowExceptionOnNull() {
    Assertions.assertThrows(Exception.class, () -> encryptionService.encodeText(null));
  }

  @Test
  void testDecryptingWithWrongEncryptedText(TestInfo testInfo) {
    String uri = "https://server.ericopoku.com/mslc-vita/user/forgot/change";
    String encryptRequestUri = encryptionService.encodeText(uri);
    Assertions.assertNotNull(encryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, uri);

    Assertions.assertThrows(Exception.class,
        () -> encryptionService.decodeText(testInfo.getDisplayName().concat(encryptRequestUri)));
  }

  @Test
  void encodeAndDecodeUri() throws UnsupportedEncodingException {
    String uri = "http://localhost:8085/mslc-vita/user/forgot/change?id=MKbscZZTo3jMpiUM"
            + "Hh2n9fZUpqtTBcLrA+TOvhithkc=&token=ybrzAV891Y/28P7kf7mhz8GlRdlmNiXdjGc3eVJrkUyI"
            + "6dnmuPTcwVxqWbWP0wsC";
    String encodeUrl = encryptionService.encodeUrl(uri);
    Assertions.assertNotNull(encodeUrl);
    Assertions.assertNotEquals(encodeUrl, uri);

    String decodedUrl = encryptionService.decodeUrl(encodeUrl);
    Assertions.assertNotNull(decodedUrl);
    Assertions.assertNotEquals(decodedUrl, encodeUrl);

    Assertions.assertEquals(uri, decodedUrl);
  }

  @Test
  void encryptAndDecryptRequestUri() {
    String uri = "http://localhost:8085/mslc-vita/user/forgot/change?id=MKbscZZTo3jMpiUM"
            + "Hh2n9fZUpqtTBcLrA+TOvhithkc=&token=ybrzAV891Y/28P7kf7mhz8GlRdlmNiXdjGc3eVJrkUyI"
            + "6dnmuPTcwVxqWbWP0wsC";
    String encryptRequestUri = encryptionService.encryptRequestUri(uri);
    Assertions.assertNotNull(encryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, uri);

    String decryptRequestUri = encryptionService.decryptRequestUri(encryptRequestUri);
    Assertions.assertNotNull(decryptRequestUri);
    Assertions.assertNotEquals(decryptRequestUri, encryptRequestUri);

    Assertions.assertEquals(uri, decryptRequestUri);
  }

  @Test
  void encryptRequestUriWithNull() {
    Assertions.assertThrows(TaxTrackerException.class, () -> encryptionService.encryptRequestUri(null));

  }

  @Test
  void encryptAndDecryptRequestWithWrongTextShouldThrowException(TestInfo testInfo) {
    String uri = "http://localhost:8085/mslc-vita/user/forgot/change?id=MKbscZZTo3jMpiUM"
            + "Hh2n9fZUpqtTBcLrA+TOvhithkc=&token=ybrzAV891Y/28P7kf7mhz8GlRdlmNiXdjGc3eVJrkUyI"
            + "6dnmuPTcwVxqWbWP0wsC";
    String encodeUrl = encryptionService.encryptRequestUri(uri);
    Assertions.assertNotNull(encodeUrl);
    Assertions.assertNotEquals(encodeUrl, uri);

    Assertions.assertThrows(TaxTrackerException.class,
        () -> encryptionService.decryptRequestUri(testInfo.getDisplayName().concat(encodeUrl)));
  }

  @Test
  void encryptAndDecryptWithStringGeneratedUuid() {
    String token = UUID.randomUUID().toString();
    String encryptRequestUri = encryptionService.encryptRequestUri(token);

    Assertions.assertNotNull(encryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, token);

    String decryptRequestUri = encryptionService.decryptRequestUri(encryptRequestUri);
    Assertions.assertNotNull(decryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, decryptRequestUri);
    Assertions.assertEquals(token, decryptRequestUri);
  }

  @Test
  void encryptAndDecryptWithUserId() {
    String userId = UserUtility.generateToken();
    String encryptRequestUri = encryptionService.encryptRequestUri(userId);

    Assertions.assertNotNull(encryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, userId);

    String decryptRequestUri = encryptionService.decryptRequestUri(encryptRequestUri);
    Assertions.assertNotNull(decryptRequestUri);
    Assertions.assertNotEquals(encryptRequestUri, decryptRequestUri);
    Assertions.assertEquals(userId, decryptRequestUri);
  }

  @Test
  void encryptAndTestMatchSuccess(TestInfo testInfo) {
    String plainText = testInfo.getDisplayName();
    String encryptedText = encryptionService.encryptText(plainText);
    Assertions.assertTrue(encryptionService.checkMatch(plainText, encryptedText));
  }

  @Test
  void encryptAndTestMatchFailure(TestInfo testInfo) {
    String plainText = testInfo.getDisplayName();
    String encryptedText = encryptionService.encryptText(plainText);
    Assertions.assertFalse(encryptionService.checkMatch(plainText.concat("_change"), encryptedText));
  }

  @Test
  void decryptRequestUrlWithPlusWithin() {
    String encrypted = "+2oExbpzsAB2lwfrM7r+/rUHnYnpZNJi65njs5RTEp8=";
    String decryptedUrl = encryptionService.decryptRequestUri(encrypted);
    Assertions.assertNotNull(decryptedUrl);
    Assertions.assertNotEquals(encrypted, decryptedUrl);
    Assertions.assertEquals("QoJjf6CgDqFTZwOa7ds5YQWMsAaQ8m", decryptedUrl);
  }
}