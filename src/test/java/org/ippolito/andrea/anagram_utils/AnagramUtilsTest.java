package org.ippolito.andrea.anagram_utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.ippolito.andrea.anagram_utils.service.AnagramService;
import org.ippolito.andrea.anagram_utils.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnagramUtilsTest {

    private final AnagramService anagramService;

    private final HistoryService historyService;

    @Autowired
    public AnagramUtilsTest(AnagramService anagramService, HistoryService historyService) {
        this.anagramService = anagramService;
        this.historyService = historyService;
    }

    @Test
    void testTwoWayCheck_WhenSameInputsRequestedMoreThanOnce_ThenComputeOnlyFirstTimeAndReturnFromHistoryAfterwards() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("cba");
        assertFalse(historyService.areKnownAnagrams(s, t));
        assertTrue(anagramService.twoWayCheck(s, t));

        assertTrue(historyService.areKnownAnagrams(s, t));
        assertTrue(anagramService.twoWayCheck(s, t));
    }
}
