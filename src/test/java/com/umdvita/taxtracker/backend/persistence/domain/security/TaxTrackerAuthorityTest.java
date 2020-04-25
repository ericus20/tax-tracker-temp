package com.umdvita.taxtracker.backend.persistence.domain.security;

import com.umdvita.taxtracker.enums.RoleType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class TaxTrackerAuthorityTest {

  private TaxTrackerAuthority taxTrackerAuthority;

  @BeforeEach
  void setUp() {
    taxTrackerAuthority = new TaxTrackerAuthority(RoleType.USER.getName());
  }

  @Test
  void testEqualsOnObjectReference(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    TaxTrackerAuthority secondTaxTrackerAuthority;
    secondTaxTrackerAuthority = localTaxTrackerAuthority;
    Assertions.assertEquals(localTaxTrackerAuthority, secondTaxTrackerAuthority);
  }

  @Test
  void testEqualsOnDifferentObjects(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    Assertions.assertNotEquals(localTaxTrackerAuthority, new Object());
  }

  @Test
  void testEqualsOnSameObjectAndSameKey(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    TaxTrackerAuthority secondTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    Assertions.assertEquals(localTaxTrackerAuthority, secondTaxTrackerAuthority);
  }

  @Test
  void testEqualsOnNulls(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    Assertions.assertNotEquals(null, localTaxTrackerAuthority);
  }

  @Test
  void testEqualsOnDifferentKeys(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    TaxTrackerAuthority secondTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName().concat("2"));
    Assertions.assertNotEquals(localTaxTrackerAuthority, secondTaxTrackerAuthority);
  }

  @Test
  void testHashCode(TestInfo testInfo) {
    TaxTrackerAuthority localTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    TaxTrackerAuthority secondTaxTrackerAuthority = new TaxTrackerAuthority(testInfo.getDisplayName());
    Assertions.assertEquals(localTaxTrackerAuthority.hashCode(), secondTaxTrackerAuthority.hashCode());
  }

  @Test
  void getAuthority() {
    Assertions.assertEquals(taxTrackerAuthority.getAuthority(), RoleType.USER.getName());
  }

  @Test
  void equalsContract() {
    EqualsVerifier.forClass(TaxTrackerAuthority.class)
            .verify();
  }

}