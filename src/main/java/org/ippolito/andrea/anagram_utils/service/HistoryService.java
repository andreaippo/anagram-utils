package org.ippolito.andrea.anagram_utils.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.collections4.SetUtils;
import org.ippolito.andrea.anagram_utils.service.model.Text;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final Map<Text, Set<Text>> history = new HashMap<>();

    public void add(Text subject, Text newMatch) {
        Set<Text> knownAnagramsSubject = getKnownAnagrams(subject);
        Set<Text> knownAnagramsNewMatch = getKnownAnagrams(newMatch);
        Set<Text> subjectChain = SetUtils.union(Set.of(subject), knownAnagramsSubject);
        Set<Text> newMatchChain = SetUtils.union(Set.of(newMatch), knownAnagramsNewMatch);

        // update the subject with the new match chain
        history.computeIfAbsent(subject, k -> new HashSet<>()).addAll(newMatchChain);
        // update the new match with the subject chain
        history.computeIfAbsent(newMatch, k -> new HashSet<>()).addAll(subjectChain);

        // update each of the known anagrams for the subject with the new match chain
        knownAnagramsSubject.forEach(
                knownAnagramSubject -> history.computeIfAbsent(knownAnagramSubject, k -> new HashSet<>())
                        .addAll(newMatchChain));
        // update each of the known anagrams for the new match with the subject chain
        knownAnagramsNewMatch.forEach(
                knownAnagramNewMatch -> history.computeIfAbsent(knownAnagramNewMatch, k -> new HashSet<>())
                        .addAll(subjectChain));
    }

    public boolean areKnownAnagrams(Text s, Text t) {
        return history.computeIfAbsent(s, k -> new HashSet<>()).contains(t);
    }

    public Set<Text> getKnownAnagrams(Text s) {
        return Optional.ofNullable(history.get(s)).map(HashSet::new).orElse(new HashSet<>());
    }
}
