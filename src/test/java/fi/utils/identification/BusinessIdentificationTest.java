package fi.utils.identification;

import org.junit.Test;

import static org.junit.Assert.*;

public class BusinessIdentificationTest {

  @Test
  public void happyPathBasicTest() {
    String id = "0000000-0";
    assertTrue(BusinessIdentification.validate(id));
  }

  @Test
  public void happyPathCalculatedValueTest() {
    String id = "0000002-7";
    assertTrue(BusinessIdentification.validate(id));
  }

  @Test
  public void happyPathOtherCalculatedValueTest() {
    String id = "1234567-1";
    assertTrue(BusinessIdentification.validate(id));
  }

  @Test
  public void invalidCharacters() {
    String id = "A000002-7";
    assertFalse(BusinessIdentification.validate(id));
  }

  @Test
  public void invalidPrefixLength() {
    String id = "000002-7";
    assertFalse(BusinessIdentification.validate(id));
  }

  @Test
  public void missingCheckDigit() {
    String id = "0000002-";
    assertFalse(BusinessIdentification.validate(id));
  }

  @Test
  public void invalidSeparatorCharacter() {
    String id = "0000002+7";
    assertFalse(BusinessIdentification.validate(id));
  }

  @Test
  public void missingSeparatorCharacter() {
    String id = "00000027";
    assertFalse(BusinessIdentification.validate(id));
  }
}
