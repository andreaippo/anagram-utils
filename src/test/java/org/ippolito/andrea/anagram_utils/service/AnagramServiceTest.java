package org.ippolito.andrea.anagram_utils.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ippolito.andrea.anagram_utils.Constants.LONELY;
import static org.ippolito.andrea.anagram_utils.Constants.MATE;
import static org.ippolito.andrea.anagram_utils.Constants.TAME;
import static org.ippolito.andrea.anagram_utils.Constants.TEAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.ippolito.andrea.anagram_utils.service.model.Text;
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
        assertTrue(anagramService.twoWayCheck(MATE, TEAM));
        verify(historyService).areKnownAnagrams(MATE, TEAM);
        verify(historyService).add(MATE, TEAM);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreAnagrams_ThenExpectTrueAndSaveResult() {
        assertTrue(anagramService.twoWayCheck(MATE, TEAM));
        verify(historyService).areKnownAnagrams(MATE, TEAM);
        verify(historyService).add(MATE, TEAM);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreNotAnagrams_ThenExpectFalseAndDontSaveResult() {
        assertFalse(anagramService.twoWayCheck(MATE, LONELY));
        verify(historyService).areKnownAnagrams(MATE, LONELY);
        verify(historyService, times(0)).add(MATE, TEAM);
    }

    @Test
    void testTwoWayCheck_WhenInputsAreKnownAnagrams_ThenExpectTrueFromHistory() {
        when(historyService.areKnownAnagrams(MATE, TEAM)).thenReturn(true);
        assertTrue(anagramService.twoWayCheck(MATE, TEAM));
        verify(historyService).areKnownAnagrams(MATE, TEAM);
        verify(historyService, times(0)).add(MATE, TEAM);
    }

    @Test
    void testHistoryCheck_WhenNoMatchInHistory_ThenReturnEmpty() {
        when(historyService.getKnownAnagrams(MATE)).thenReturn(Set.of());
        assertThat(anagramService.historicalCheck(MATE)).isEmpty();
    }

    @Test
    void testHistoryCheck_WhenMatchesInHistory_ThenReturnMatches() {
        Set<Text> fromHistory = Set.of(TEAM, TAME);
        when(historyService.getKnownAnagrams(MATE)).thenReturn(fromHistory);
        assertEquals(fromHistory, anagramService.historicalCheck(MATE));
    }
}
