package org.ippolito.andrea.anagram_utils.service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ippolito.andrea.anagram_utils.service.model.Text.MAX_LENGTH;
import static org.ippolito.andrea.anagram_utils.service.model.Text.TEXT_CANNOT_BE_EMPTY;
import static org.ippolito.andrea.anagram_utils.service.model.Text.TEXT_CONTAINS_INVALID_CHARS_MSG;
import static org.ippolito.andrea.anagram_utils.service.model.Text.TEXT_TOO_LONG_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class TextTest {

    @Test
    void whenInputIsBlank_ThenThrowException() {
        String input = StringUtils.EMPTY;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Text(input));
        assertThat(exception.getMessage()).isEqualTo(TEXT_CANNOT_BE_EMPTY);
    }

    @Test
    void whenInputLengthOk_ThenNoException() {
        String input = RandomStringUtils.randomAlphabetic(10);
        Text text = new Text(input);
        assertEquals(input.toLowerCase(), text.input());
    }

    @Test
    void whenInputTooLong_ThenThrowException() {
        String input = RandomStringUtils.randomAlphabetic(MAX_LENGTH + 1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Text(input));
        assertThat(exception.getMessage()).isEqualTo(TEXT_TOO_LONG_MSG);
    }

    @Test
    void whenInputLettersOrSpace_ThenNoException() {
        String input = RandomStringUtils.randomAlphabetic(10) + StringUtils.SPACE + "ә";
        Text text = new Text(input);
        assertEquals(input.toLowerCase(), text.input());
    }

    @Test
    void whenInputContainsInvalidCharacters_ThenThrowException() {
        String input = RandomStringUtils.randomAlphanumeric(10) + ".";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Text(input));
        assertThat(exception.getMessage()).isEqualTo(TEXT_CONTAINS_INVALID_CHARS_MSG);
    }
}
