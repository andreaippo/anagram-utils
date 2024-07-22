package org.ippolito.andrea.anagram_utils.service;

import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceTest {

  private HistoryService historyService;

  @BeforeEach
  void setUp() {
    historyService = new HistoryService();
  }

  @Test
  void whenInputsAreSameAndAssociationAdded_ThenDirectAndReverseLookupOk() {
    AnagramInput s = new AnagramInput("abc");
    AnagramInput t = new AnagramInput("abc");
    historyService.add(s, t);
    assertTrue(historyService.areKnownAnagrams(s, t));
    assertThat(historyService.getKnownAnagrams(s)).containsExactly(t);
    assertThat(historyService.getKnownAnagrams(t)).containsExactly(s);
  }

  @Test
  void whenAssociationAdded_ThenDirectAndReverseLookupOk() {
    AnagramInput s = new AnagramInput("abc");
    AnagramInput t = new AnagramInput("cba");
    historyService.add(s, t);
    assertTrue(historyService.areKnownAnagrams(s, t));
    assertThat(historyService.getKnownAnagrams(s)).containsExactly(t);
    assertThat(historyService.getKnownAnagrams(t)).containsExactly(s);
  }

}