package org.ippolito.andrea.anagram_utils.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ippolito.andrea.anagram_utils.Constants.MATE;
import static org.ippolito.andrea.anagram_utils.Constants.MEAT;
import static org.ippolito.andrea.anagram_utils.Constants.TAME;
import static org.ippolito.andrea.anagram_utils.Constants.TEAM;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryServiceTest {

  private HistoryService historyService;

  @BeforeEach
  void setUp() {
    historyService = new HistoryService();
  }

  @Test
  void whenInputsAreSameAndAssociationAdded_ThenDirectAndReverseLookupOk() {
    historyService.add(MATE, MATE);
    assertTrue(historyService.areKnownAnagrams(MATE, MATE));
    assertThat(historyService.getKnownAnagrams(MATE)).containsOnly(MATE);
    assertThat(historyService.getKnownAnagrams(MATE)).containsOnly(MATE);
  }

  @Test
  void whenAssociationAdded_ThenDirectAndReverseLookupOk() {
    historyService.add(MATE, TEAM);
    assertTrue(historyService.areKnownAnagrams(MATE, TEAM));
    assertThat(historyService.getKnownAnagrams(MATE)).containsOnly(TEAM);
    assertThat(historyService.getKnownAnagrams(TEAM)).containsOnly(MATE);
  }

  @Test
  void whenAssociationAdded_ThenAssociationPropagatedTransitively() {
    historyService.add(MATE, MEAT);
    assertTrue(historyService.areKnownAnagrams(MATE, MEAT));
    assertThat(historyService.getKnownAnagrams(MATE)).containsOnly(MEAT);
    assertThat(historyService.getKnownAnagrams(MEAT)).containsOnly(MATE);

    historyService.add(TEAM, TAME);
    assertTrue(historyService.areKnownAnagrams(TEAM, TAME));
    assertThat(historyService.getKnownAnagrams(TEAM)).containsOnly(TAME);
    assertThat(historyService.getKnownAnagrams(TAME)).containsOnly(TEAM);

    historyService.add(TEAM, MATE);
    assertTrue(historyService.areKnownAnagrams(TEAM, MATE));
    assertThat(historyService.getKnownAnagrams(MATE)).containsOnly(MEAT, TAME, TEAM);
    assertThat(historyService.getKnownAnagrams(MEAT)).containsOnly(MATE, TAME, TEAM);
    assertThat(historyService.getKnownAnagrams(TAME)).containsOnly(MATE, MEAT, TEAM);
    assertThat(historyService.getKnownAnagrams(TEAM)).containsOnly(MATE, MEAT, TAME);
  }
}
