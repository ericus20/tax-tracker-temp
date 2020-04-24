package com.umdvita.taxtracker.backend.persistence.domain.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * This tests the basic functionality of the BaseEntity class.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
class BaseEntityTest {

  private BaseEntity<Long> baseEntity;

  /**
   * Initializes a base entity object to be used in the test.
   */
  @BeforeEach
  void setUp(TestInfo testInfo) {
    baseEntity = new BaseEntity<>();
    baseEntity.setId(1L);
    baseEntity.setVersion((short) 1);
    baseEntity.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
    baseEntity.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()).plusMinutes(5));
    baseEntity.setCreatedBy(testInfo.getDisplayName());
  }

  @Test
  void getId() {
    Assertions.assertEquals(baseEntity.getId(), Long.valueOf(1));
  }

  @Test
  void getVersion() {
    baseEntity.setVersion((short) 2);
    Assertions.assertEquals(2, baseEntity.getVersion());
  }

  @Test
  void getCreatedAt() {
    Assertions.assertNotNull(baseEntity.getCreatedAt());
  }

  @Test
  void getCreatedBy() {
    Assertions.assertNotNull(baseEntity.getCreatedBy());
  }

  @Test
  void getUpdatedAt() {
    Assertions.assertNotNull(baseEntity.getCreatedAt());
  }

  @Test
  void getUpdatedBy() {
    Assertions.assertTrue(baseEntity.getUpdatedAt().isAfter(LocalDateTime.now(Clock.systemUTC())));
  }

  @Test
  void setId() {
    baseEntity.setId(2L);
    Assertions.assertEquals(baseEntity.getId(), Long.valueOf(2));
  }

  @Test
  void setCreatedAt() {
    LocalDateTime old = baseEntity.getCreatedAt();
    baseEntity.setCreatedAt(baseEntity.getCreatedAt().plusMinutes(2));
    Assertions.assertTrue(baseEntity.getCreatedAt().isAfter(old));
  }

  @Test
  void setCreatedBy(TestInfo testInfo) {
    baseEntity.setCreatedBy(testInfo.getDisplayName());
    Assertions.assertEquals(baseEntity.getCreatedBy(), testInfo.getDisplayName());
  }

  @Test
  void setUpdatedAt() {
    LocalDateTime old = baseEntity.getUpdatedAt();
    baseEntity.setUpdatedAt(baseEntity.getUpdatedAt().plusMinutes(2));
    Assertions.assertTrue(baseEntity.getUpdatedAt().isAfter(old));
  }

  @Test
  void setUpdatedBy(TestInfo testInfo) {
    baseEntity.setUpdatedBy(testInfo.getDisplayName());
    Assertions.assertEquals(baseEntity.getUpdatedBy(), testInfo.getDisplayName());
  }

  @Test
  void equalsNotSame() {
    BaseEntity<Long> baseEntity2 = new BaseEntity<>();
    baseEntity.setVersion((short) 1);
    baseEntity2.setVersion((short) 2);
    Assertions.assertNotEquals(baseEntity2, baseEntity);
  }

  @Test
  void equalsOnNull() {
    Assertions.assertNotEquals(null, baseEntity);
  }

  @Test
  void hashCodeNotSame() {
    Assertions.assertNotEquals(baseEntity.hashCode(), baseEntity.getVersion());
  }

  @Test
  void canEqualNotOnNull() {
    Assertions.assertFalse(baseEntity.canEqual(null));
  }

  @Test
  void canEqual() {
    BaseEntity<Long> baseEntity2 = new BaseEntity<>();
    baseEntity.setVersion((short) 2);
    baseEntity2.setVersion((short) 2);
    Assertions.assertTrue(baseEntity.canEqual(baseEntity2));
  }

  @Test
  void equalsSame() {
    BaseEntity<Long> baseEntity2 = baseEntity;
    baseEntity.setVersion((short) 1);
    baseEntity2.setVersion((short) 1);
    Assertions.assertEquals(baseEntity2, baseEntity);
  }
}