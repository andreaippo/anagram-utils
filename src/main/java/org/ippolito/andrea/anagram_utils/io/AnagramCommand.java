package org.ippolito.andrea.anagram_utils.io;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.ippolito.andrea.anagram_utils.service.AnagramService;
import org.ippolito.andrea.anagram_utils.service.model.Text;
import org.springframework.shell.command.annotation.Command;

@Slf4j
@Command(group = "Anagram utilities")
@RequiredArgsConstructor
public class AnagramCommand {

    private final AnagramService anagramService;

    @Command(command = "check", description = "Verifies if two texts provided are anagrams of each other")
    public String check(String first, String second) {
        Text ai1 = new Text(first);
        Text ai2 = new Text(second);
        return "Texts are anagrams: %s".formatted(BooleanUtils.toStringYesNo(anagramService.twoWayCheck(ai1, ai2)));
    }

    @Command(
            command = "history",
            description = "Given an input text, returns its known anagrams among all the texts "
                    + "provided to the check command during this session")
    public String history(String input) {
        Text ai = new Text(input);
        return "List of known anagrams %s"
                .formatted(anagramService.historySearch(ai).stream()
                        .map(Text::input)
                        .collect(Collectors.toSet()));
    }
}
