package org.ippolito.andrea.anagram_utils.model;

public record AnagramSubject(AnagramInput anagramInput) {

  boolean isAnagram(AnagramInput candidate) {
    return false;
  }
}
