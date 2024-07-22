package org.ippolito.andrea.anagram_utils.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ippolito.andrea.anagram_utils.model.AnagramInput.INPUT_CANNOT_BE_EMPTY;
import static org.ippolito.andrea.anagram_utils.model.AnagramInput.INPUT_CONTAINS_INVALID_CHARS_MSG;
import static org.ippolito.andrea.anagram_utils.model.AnagramInput.INPUT_TOO_LONG_MSG;
import static org.ippolito.andrea.anagram_utils.model.AnagramInput.MAX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnagramInputTest {

  @Test
  void whenInputIsBlank_ThenThrowException() {
    String input = StringUtils.EMPTY;
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new AnagramInput(input));
    assertThat(exception.getMessage()).isEqualTo(INPUT_CANNOT_BE_EMPTY);

  }

  @Test
  void whenInputLengthOk_ThenNoException() {
    String input = RandomStringUtils.randomAlphabetic(10);
    AnagramInput anagramInput = new AnagramInput(input);
    assertDoesNotThrow(() -> anagramInput);
    assertEquals(input, anagramInput.input());
  }

  @Test
  void whenInputTooLong_ThenThrowException() {
    String input = RandomStringUtils.randomAlphabetic(MAX_LENGTH + 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new AnagramInput(input));
    assertThat(exception.getMessage()).isEqualTo(INPUT_TOO_LONG_MSG);
  }

  @Test
  void whenInputLettersOrSpace_ThenNoException() {
    String input = RandomStringUtils.randomAlphabetic(10) + StringUtils.SPACE + "ә";
    AnagramInput anagramInput = new AnagramInput(input);
    assertDoesNotThrow(() -> anagramInput);
    assertEquals(input, anagramInput.input());
    System.out.println(input);
  }

  @Test
  void whenInputContainsInvalidCharacters_ThenThrowException() {
    String input = RandomStringUtils.randomAlphanumeric(10) + StringUtils.SPACE;
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new AnagramInput(input));
    assertThat(exception.getMessage()).isEqualTo(INPUT_CONTAINS_INVALID_CHARS_MSG);
  }

}