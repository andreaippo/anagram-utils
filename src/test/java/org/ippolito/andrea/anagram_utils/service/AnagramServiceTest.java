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
    void testTwoWayCheck_WhenInputsAreAnagramsButDifferentWordCount_ThenExpectTrue() {
        Text seemsNiceGuy = new Text("Tom Marvolo Riddle");
        // please only whisper his name :)
        Text worstVillainEver = new Text("I am Lord Voldemort");
        assertTrue(anagramService.twoWayCheck(seemsNiceGuy, worstVillainEver));
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
    void testTwoWayCheck_WhenInputsOnlyDifferInPunctuationAndSpaces_ThenExpectTrue() {
        // https://en.wikipedia.org/wiki/Anagram#Examples
        Text case1Text1 = new Text("Church of Scientology");
        Text case1Text2 = new Text("rich-chosen goofy cult");
        assertTrue(anagramService.twoWayCheck(case1Text1, case1Text2));

        Text case2Text1 = new Text("McDonald's restaurants");
        Text case2Text2 = new Text("Uncle Sam's standard rot");
        assertTrue(anagramService.twoWayCheck(case2Text1, case2Text2));

        Text case3Text1 = new Text("She Sells Sanctuary");
        Text case3Text2 = new Text("Santa; shy, less cruel");
        Text case3Text3 = new Text("Satan; cruel, less shy");
        assertTrue(anagramService.twoWayCheck(case3Text1, case3Text2));
        assertTrue(anagramService.twoWayCheck(case3Text1, case3Text3));
    }

    @Test
    void testHistoryCheck_WhenNoMatchInHistory_ThenReturnEmpty() {
        when(historyService.getKnownAnagrams(MATE)).thenReturn(Set.of());
        assertThat(anagramService.historySearch(MATE)).isEmpty();
    }

    @Test
    void testHistoryCheck_WhenMatchesInHistory_ThenReturnMatches() {
        Set<Text> fromHistory = Set.of(TEAM, TAME);
        when(historyService.getKnownAnagrams(MATE)).thenReturn(fromHistory);
        assertEquals(fromHistory, anagramService.historySearch(MATE));
    }
}
