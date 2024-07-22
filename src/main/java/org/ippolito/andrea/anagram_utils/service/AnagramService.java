package org.ippolito.andrea.anagram_utils.service;

import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AnagramService {

  private final HistoryService historyService;

  public AnagramService(HistoryService historyService) {
    this.historyService = historyService;
  }

  // TODO define a return type that conveys reason for check unsuccessful (i.e. different length VS same length but
  //  not anagrams), or check successful
  public boolean twoWayCheck(AnagramInput s, AnagramInput t) {
    // use historyService as a cache here
    if (historyService.areKnownAnagrams(s, t)) {
      return true;
    }

    final boolean areAnagrams = computeTwoWayCheck(s, t);

    if (areAnagrams) {
      historyService.add(s, t);
    }
    return areAnagrams;
  }

  public Set<AnagramInput> historicalCheck(AnagramInput s) {
    return historyService.getKnownAnagrams(s);
  }

  private static boolean computeTwoWayCheck(AnagramInput s, AnagramInput t) {
    if (s.input().length() != t.input().length()) {
      return false;
    }
    Map<Character, Integer> frequencyMap = new HashMap<>();
    char[] sChars = s.input().toCharArray();
    for (char ch : sChars) {
      frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
    }

    char[] tChars = t.input().toCharArray();
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
