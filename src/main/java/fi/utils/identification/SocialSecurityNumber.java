package fi.utils.identification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Logic explained: http://www.vrk.fi/default.aspx?id=167
 */
public class SocialSecurityNumber {

  private static final Map<Integer, Character> checkCharacter;

  static {
    checkCharacter = new HashMap<Integer, Character>();
    checkCharacter.put(0, '0');
    checkCharacter.put(1, '1');
    checkCharacter.put(2, '2');
    checkCharacter.put(3, '3');
    checkCharacter.put(4, '4');
    checkCharacter.put(5, '5');
    checkCharacter.put(6, '6');
    checkCharacter.put(7, '7');
    checkCharacter.put(8, '8');
    checkCharacter.put(9, '9');
    checkCharacter.put(10, 'A');
    checkCharacter.put(11, 'B');
    checkCharacter.put(12, 'C');
    checkCharacter.put(13, 'D');
    checkCharacter.put(14, 'E');
    checkCharacter.put(15, 'F');
    checkCharacter.put(16, 'H');
    checkCharacter.put(17, 'J');
    checkCharacter.put(18, 'K');
    checkCharacter.put(19, 'L');
    checkCharacter.put(20, 'M');
    checkCharacter.put(21, 'N');
    checkCharacter.put(22, 'P');
    checkCharacter.put(23, 'R');
    checkCharacter.put(24, 'S');
    checkCharacter.put(25, 'T');
    checkCharacter.put(26, 'U');
    checkCharacter.put(27, 'V');
    checkCharacter.put(28, 'W');
    checkCharacter.put(29, 'X');
    checkCharacter.put(30, 'Y');
  }

  private static final Integer SSN_LENGTH = 10;
  private static final Integer SSN_DATE_LENGTH = 6;
  private static final Integer SSN_SUFFIX_START_POSITION = 7;
  private static final Integer DIVIDER = 31;

  public static boolean validate(String socialSecurityNumber) {
    return validatePattern(socialSecurityNumber) && validateDate(socialSecurityNumber) && validateSuffix(socialSecurityNumber) && validateCheckCharacter(socialSecurityNumber);
  }

  private static boolean validateCheckCharacter(String socialSecurityNumber) {
    String lastCharOfSSN = socialSecurityNumber.substring(SSN_LENGTH).toUpperCase();
    String allButLastChar = socialSecurityNumber.substring(0, SSN_LENGTH);
    String digits = allButLastChar.replaceAll("[^\\d]+", "");
    Integer checkCharKey = Integer.parseInt(digits) % DIVIDER;

    return checkCharacter.get(checkCharKey).equals(lastCharOfSSN.charAt(0));
  }

  private static boolean validateSuffix(String socialSecurityNumber) {
    String suffix = socialSecurityNumber.substring(SSN_SUFFIX_START_POSITION, SSN_LENGTH);
    Integer suffixNumber = Integer.parseInt(suffix);
    return suffixNumber >= 2 && suffixNumber <= 899;
  }

  private static boolean validateDate(String socialSecurityNumber) {
    boolean validSsnDate = true;
    SimpleDateFormat ssnDateFormat = new SimpleDateFormat("ddMMyy");
    ssnDateFormat.setLenient(false);

    try {
      ssnDateFormat.parse(socialSecurityNumber.substring(0, SSN_DATE_LENGTH));
    } catch (ParseException e) {
      System.err.println("Social security number date part isn't a valid date: " + socialSecurityNumber.substring(0, SSN_DATE_LENGTH));
      e.printStackTrace();
      validSsnDate = false;
    }
    return validSsnDate;
  }

  private static boolean validatePattern(String socialSecurityNumber) {
    String SSNpattern = "^[\\d]{6}[-|A|+][\\d]{3}[\\w|\\d]";
    return Pattern.matches(SSNpattern, socialSecurityNumber);
  }
}
