package org.ippolito.andrea.anagram_utils.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ippolito.andrea.anagram_utils.service.model.Text;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnagramService {

    private final HistoryService historyService;

    public AnagramService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public boolean twoWayCheck(Text s, Text t) {
        log.debug("two-way check between {} and {}", s, t);
        // use historyService as a cache here
        if (historyService.areKnownAnagrams(s, t)) {
            log.debug("known anagrams from history, skipping computation");
            return true;
        }

        final boolean areAnagrams = computeTwoWayCheck(s, t);

        if (areAnagrams) {
            historyService.add(s, t);
        }
        return areAnagrams;
    }

    public Set<Text> historySearch(Text s) {
        return historyService.getKnownAnagrams(s);
    }

    private static boolean computeTwoWayCheck(Text s, Text t) {
        // make comparison lenient with respect to spaces
        String sWithoutSpaces = StringUtils.deleteWhitespace(s.input());
        String tWithoutSpaces = StringUtils.deleteWhitespace(t.input());

        if (sWithoutSpaces.length() != tWithoutSpaces.length()) {
            return false;
        }
        Map<Character, Integer> frequencyMap = new HashMap<>();
        char[] sChars = sWithoutSpaces.toCharArray();
        for (char ch : sChars) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }

        char[] tChars = tWithoutSpaces.toCharArray();
        for (char ch : tChars) {
            Integer frequency = frequencyMap.get(ch);
            if (frequency == null) {
                return false;
            }
            frequencyMap.put(ch, frequency - 1);
        }
        return frequencyMap.values().stream().allMatch(value -> value == 0);
    }
}
