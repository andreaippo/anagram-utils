package org.ippolito.andrea.anagram_utils.service;

import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnagramServiceTest {

  private final AnagramService anagramService = new AnagramService();

  @Test
  void whenInputsAreAnagrams_ThenExpectTrue() {
    AnagramInput s = new AnagramInput("abc");
    AnagramInput t = new AnagramInput("abc");
    assertTrue(anagramService.twoWayCheck(s, t));
  }

  @Test
  void whenInputsAreNotAnagrams_ThenExpectFalse() {
    AnagramInput s = new AnagramInput("abc");
    AnagramInput t = new AnagramInput("def");
    assertFalse(anagramService.twoWayCheck(s, t));
  }

}