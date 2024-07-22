package org.ippolito.andrea.anagram_utils.service.model;

import org.apache.commons.lang3.StringUtils;

public record Text(String input) {

    static final int MAX_LENGTH = 1_000_000;

    static final String TEXT_CANNOT_BE_EMPTY = "Text cannot be empty";

    static final String TEXT_TOO_LONG_MSG = "Text cannot exceed %s".formatted(MAX_LENGTH);

    static final String TEXT_CONTAINS_INVALID_CHARS_MSG = "Text contains invalid characters";

    public Text {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException(TEXT_CANNOT_BE_EMPTY);
        }
        if (StringUtils.length(input) > MAX_LENGTH) {
            throw new IllegalArgumentException(TEXT_TOO_LONG_MSG);
        }
        if (!StringUtils.isAlphaSpace(input)) {
            throw new IllegalArgumentException(TEXT_CONTAINS_INVALID_CHARS_MSG);
        }
        input = StringUtils.lowerCase(input);
    }
}
