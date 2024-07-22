package org.ippolito.andrea.anagram_utils.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.ippolito.andrea.anagram_utils.model.AnagramInput;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final Map<AnagramInput, Set<AnagramInput>> anagrams = new HashMap<>();

    public void add(AnagramInput s, AnagramInput t) {
        anagrams.computeIfAbsent(s, k -> new HashSet<>()).add(t);
        anagrams.computeIfAbsent(t, k -> new HashSet<>()).add(s);
    }

    public boolean areKnownAnagrams(AnagramInput s, AnagramInput t) {
        return anagrams.computeIfAbsent(s, k -> new HashSet<>()).contains(t);
    }

    public Set<AnagramInput> getKnownAnagrams(AnagramInput s) {
        return anagrams.get(s);
    }
}
