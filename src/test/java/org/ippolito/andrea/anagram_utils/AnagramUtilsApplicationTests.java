package org.ippolito.andrea.anagram_utils;

import org.ippolito.andrea.anagram_utils.service.AnagramService;
import org.ippolito.andrea.anagram_utils.service.HistoryService;
import org.ippolito.andrea.anagram_utils.service.model.Text;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ippolito.andrea.anagram_utils.Constants.LONELY;
import static org.ippolito.andrea.anagram_utils.Constants.MATE;
import static org.ippolito.andrea.anagram_utils.Constants.MEAT;
import static org.ippolito.andrea.anagram_utils.Constants.TAME;
import static org.ippolito.andrea.anagram_utils.Constants.TEAM;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("integration-tests")
class AnagramUtilsApplicationTests {

  private final AnagramService anagramService;

  private final HistoryService historyService;

  @Autowired
  public AnagramUtilsApplicationTests(AnagramService anagramService, HistoryService historyService) {
    this.anagramService = anagramService;
    this.historyService = historyService;
  }

  @Test
  void testTwoWayCheck_WhenSameInputsRequestedMoreThanOnce_ThenComputeOnlyFirstTimeAndReturnFromHistoryAfterwards() {
    assertFalse(historyService.areKnownAnagrams(MATE, MEAT));
    assertTrue(anagramService.twoWayCheck(MATE, MEAT));

    assertTrue(historyService.areKnownAnagrams(MATE, MEAT));
    assertTrue(anagramService.twoWayCheck(MATE, MEAT));
  }

  @Test
  void testAssignmentAcceptanceCriteriaAreMet() {
    Text A = TEAM;
    Text B = MATE;
    Text C = LONELY;
    Text D = TAME;

    // first check
    anagramService.twoWayCheck(A, B);
    // second check
    anagramService.twoWayCheck(A, C);
    // third check
    anagramService.twoWayCheck(A, D);

    assertThat(historyService.getKnownAnagrams(A)).containsOnly(B, D);
    assertThat(historyService.getKnownAnagrams(B)).containsOnly(A, D);
    assertThat(historyService.getKnownAnagrams(C)).isEmpty();

  }

}
