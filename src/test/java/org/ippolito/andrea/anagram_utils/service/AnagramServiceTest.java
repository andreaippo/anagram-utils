package org.ippolito.andrea.anagram_utils.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnagramServiceTest {

    @InjectMocks
    private AnagramService anagramService;

    @Mock
    private HistoryService historyService;

    @Test
    void testTwoWayCheck_WhenInputsAreSame_ThenExpectTrueAndSaveResult() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("abc");
        assertTrue(anagramService.twoWayCheck(s, t));
        verify(historyService).areKnownAnagrams(s, t);
        verify(historyService).add(s, t);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreAnagrams_ThenExpectTrueAndSaveResult() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("cba");
        assertTrue(anagramService.twoWayCheck(s, t));
        verify(historyService).areKnownAnagrams(s, t);
        verify(historyService).add(s, t);
    }

    @Test
    void testTwoWayCheck_WhenInputsHaveDifferentLength_ThenExpectFalseAndDontSaveResult() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("de");
        assertFalse(anagramService.twoWayCheck(s, t));
        verify(historyService).areKnownAnagrams(s, t);
        verify(historyService, times(0)).add(s, t);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreNotAnagrams_ThenExpectFalseAndDontSaveResult() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("def");
        assertFalse(anagramService.twoWayCheck(s, t));
        verify(historyService).areKnownAnagrams(s, t);
        verify(historyService, times(0)).add(s, t);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreKnownAnagrams_ThenExpectTrueFromHistory() {
        AnagramInput s = new AnagramInput("abc");
        AnagramInput t = new AnagramInput("cba");
        when(historyService.areKnownAnagrams(s, t)).thenReturn(true);
        assertTrue(anagramService.twoWayCheck(s, t));
        verify(historyService).areKnownAnagrams(s, t);
        verify(historyService, times(0)).add(s, t);
    }

    @Test
    void testHistoryCheck_WhenNoMatchInHistory_ThenReturnEmpty() {
        AnagramInput s = new AnagramInput("abc");
        when(historyService.getKnownAnagrams(s)).thenReturn(Set.of());
        assertThat(anagramService.historicalCheck(s)).isEmpty();
    }

    @Test
    void testHistoryCheck_WhenMatchesInHistory_ThenReturnMatches() {
        AnagramInput s = new AnagramInput("abc");
        Set<AnagramInput> fromHistory = Set.of(new AnagramInput("cba"), new AnagramInput("bca"));
        when(historyService.getKnownAnagrams(s)).thenReturn(fromHistory);
        assertEquals(fromHistory, anagramService.historicalCheck(s));
    }
}
