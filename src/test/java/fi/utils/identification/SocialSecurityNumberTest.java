package fi.utils.identification;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocialSecurityNumberTest {

  @Test
  public void happyPathBasicTest() {
    String ssn = "010101-0101";
    assertTrue(SocialSecurityNumber.validate(ssn));
  }

  @Test
  public void testSSNEndingWithLetter() {
    String ssn = "191187-123J";
    assertTrue(SocialSecurityNumber.validate(ssn));
  }

  @Test
  public void failingBasicTest() {
    String ssn = "010101-010A";
    assertFalse(SocialSecurityNumber.validate(ssn));
  }

  @Test
  public void validSeparatorChars() {
    String ssnWithDash = "010101-0101";
    String ssnWithPlus = "010101+0101";
    String ssnWithA = "010101A0101";
    assertTrue(SocialSecurityNumber.validate(ssnWithDash));
    assertTrue(SocialSecurityNumber.validate(ssnWithPlus));
    assertTrue(SocialSecurityNumber.validate(ssnWithA));
  }

  @Test
  public void invalidSeparatorChars() {
    String ssnWithLetter = "010101B0101";
    String ssnWithNumber = "01010190101";
    String ssnWithSpecialCharacter = "010101&0101";
    assertFalse(SocialSecurityNumber.validate(ssnWithLetter));
    assertFalse(SocialSecurityNumber.validate(ssnWithNumber));
    assertFalse(SocialSecurityNumber.validate(ssnWithSpecialCharacter));
  }

  @Test
  public void tooShortDatePart() {
    String ssnWithInvalidYear = "01011-0101";
    String ssnWithoutYear = "0101-0101";
    String ssnWithOutMonthAndYear = "01-0101";
    assertFalse(SocialSecurityNumber.validate(ssnWithInvalidYear));
    assertFalse(SocialSecurityNumber.validate(ssnWithoutYear));
    assertFalse(SocialSecurityNumber.validate(ssnWithOutMonthAndYear));
  }

  @Test
  public void tooLongDatePart() {
    String ssnWithInvalidYear = "01011901-0101";
    assertFalse(SocialSecurityNumber.validate(ssnWithInvalidYear));
  }

  @Test
  public void invalidSecondPart() {
    String ssnPrefixSizeThree = "010101-101";
    String ssnPrefixSizeTwo = "010101-10";
    String ssnPrefixSizeOne = "010101-1";
    String ssnPrefixSizeFive = "010101-01010";
    String ssnPrefixMissing = "010101-";
    assertFalse(SocialSecurityNumber.validate(ssnPrefixSizeFive));
    assertFalse(SocialSecurityNumber.validate(ssnPrefixSizeThree));
    assertFalse(SocialSecurityNumber.validate(ssnPrefixSizeTwo));
    assertFalse(SocialSecurityNumber.validate(ssnPrefixSizeOne));
    assertFalse(SocialSecurityNumber.validate(ssnPrefixMissing));
  }

  @Test
  public void invalidDate() {
    String ssn = "011397-1832";
    assertFalse(SocialSecurityNumber.validate(ssn));
  }
}
