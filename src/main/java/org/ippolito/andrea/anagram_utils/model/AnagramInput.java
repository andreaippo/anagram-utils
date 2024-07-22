package org.ippolito.andrea.anagram_utils.model;

import org.apache.commons.lang3.StringUtils;

public record AnagramInput(String input) {

  static final int MAX_LENGTH = 1_000_000;

  static final String INPUT_CANNOT_BE_EMPTY = "Input cannot be empty";

  static final String INPUT_TOO_LONG_MSG = "Input cannot exceed %s".formatted(MAX_LENGTH);

  static final String INPUT_CONTAINS_INVALID_CHARS_MSG = "Input contains invalid characters";

  public AnagramInput {
    if (StringUtils.isBlank(input)) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    }
    if (StringUtils.length(input) > MAX_LENGTH) {
      throw new IllegalArgumentException(INPUT_TOO_LONG_MSG);
    }
    if (!StringUtils.isAlphaSpace(input)) {
      throw new IllegalArgumentException(INPUT_CONTAINS_INVALID_CHARS_MSG);
    }
  }
}
