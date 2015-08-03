package fi.utils.identification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Logic explained: http://www.vero.fi/download/Tarkistusmerkin_laskenta/%7BD5780347-547E-4C44-90C1-25F8AD9DA7F8%7D/6508
 */
public class BusinessIdentification {

  private static Integer IDENTIFICATION_PREFIX_LENGTH = 7;
  private static Integer IDENTIFICATION_LENGTH = 9;
  private static Integer DIVIDER = 11;
  private static List<Integer> MULTIPLIERS = Arrays.asList(7, 9, 10, 5, 8, 4, 2);

  public static Boolean validate(String businessIdentification) {
    return validatePattern(businessIdentification) && validateCheckDigit(businessIdentification);
  }

  private static boolean validateCheckDigit(String businessIdentification) {
    Integer checkDigit = Integer.parseInt(businessIdentification.substring(IDENTIFICATION_LENGTH-1));
    char[] digits = businessIdentification.substring(0, IDENTIFICATION_PREFIX_LENGTH).toCharArray();
    
    Integer remainder = calculateRemainder(digits);
    Optional<Integer> calculatedCheckDigit = Optional.empty();

    if (remainder == 0) {
      calculatedCheckDigit = Optional.of(0);
    } else if (remainder > 1) {
      calculatedCheckDigit = Optional.of(DIVIDER - remainder);
    }

    return calculatedCheckDigit.isPresent() && calculatedCheckDigit.get() == checkDigit;
  }

  private static Integer calculateRemainder(char[] digits) {
    Integer sumIdentificationPrefix = 0;

    for (int i = 0; i < IDENTIFICATION_PREFIX_LENGTH; i++) {
      Integer digit = Character.getNumericValue(digits[i]);
      Integer multiplier = MULTIPLIERS.get(i);
      sumIdentificationPrefix += digit * multiplier;
    }

    return sumIdentificationPrefix % DIVIDER;
  }
  
  private static boolean validatePattern(String businessIdentification) {
    String businessIdentificationPattern = "^[\\d]{7}[-][\\d]";
    return Pattern.matches(businessIdentificationPattern, businessIdentification);
  }
}
