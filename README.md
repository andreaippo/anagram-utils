# Anagram Utilities

## Overview

This package provides two utility features related to anagrams.

> An anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all
> the original letters exactly once. For example, the word anagram itself can be rearranged into the nonsense
> phrase "nag a ram".
>
> [..]
>
> The original word or phrase is known as the subject of the anagram.
>
> [(Wikipedia)](https://en.wikipedia.org/wiki/Anagram)

### Two-way check

_Corresponds to feature #1 in the assignment_

This feature verifies if two texts provided are anagrams of each other.

### History check

_Corresponds to feature #2 in the assignment_

This feature accepts an input text, and returns all the known anagrams for such input, by considering exclusively
the inputs that have been previously supplied to the two-way check feature (within the same execution of the program).

## Build

Pre-requisites:

- Apache Maven
- JDK 21

Execute from the project's root directory:

`mvn clean install`

## Run

There are two options, please refer to the sections below.

### As executable on the host machine

Pre-requisites:

- [Build step](#Build) executed successfully
- JRE 21 installed and on the `PATH`

Execute from the project's root directory:

`java -jar target/anagram-utils-0.0.1-SNAPSHOT.jar`

### As a docker/podman container

Pre-requisites:

- `docker` or `podman`

Execute from the project's root directory:

`docker build . -t anagram-utils && docker run -it anagram-utils`

## Usage

Once the app is running, you are greeted with a shell prompt:

`shell:>`

Type `help` to see a list of available commands, or one of `check` or `history`.

Examples (with their outputs):

```shell
shell:>check team MATE
Texts are anagrams: yes

shell:>check team meat
Texts are anagrams: yes

shell:>history meat
List of known anagrams [mate, team]
```

Multi-word (use quotes `"` to surround inputs):

```shell
shell:>check "President Obama" "a baptism redone"
Texts are anagrams: yes

shell:>history "President Obama"
List of known anagrams [a baptism redone]

shell:>check "McDonald's restaurants" "Uncle Sam's standard rot"
Texts are anagrams: yes

shell:>check --first "President Obama" --second "a baptism redone"
Texts are anagrams: yes
```

## Observations

We assume an input text to be valid if it meets the following conditions:

- it is not empty
- it does not exceed `MAX_LENGTH`
- it only contains `alpha` and space characters, as defined
  by [Apache's commons-lang library](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isAlphaSpace(java.lang.CharSequence\)),
  plus punctuation characters

A `Text` acts as a wrapper for inputs that are valid according to the aforementioned definition, i.e. no
`Text`s wrapping invalid inputs can be created. Inputs are also lower-cased upon creation of the corresponding `Text`.

The two-way check was implemented in a whitespace-independent way. This gives some flexibility to recognize two
`Text`s as anagrams when their letters match, even though the word count is different (therefore overall length and
whitespace count differs).

The same applies to punctuation characters.

Because of this, `She Sells Sanctuary` and `Santa; shy, less cruel` will be accepted as anagrams, although they
differ in word/spaces count and punctuation.

### Choices

I have opted for a CLI interface, although I don't really have much experience with it, but a REST API seemed
excessive, given the reduced scope of the assignment.

If I had opted for a REST API, I would have defined:

- a `POST` to `/anagram-utils/text`, where `text` is the subject, and the body contains the candidate text. The response
  body could then return a simple boolean. I would use a `POST` because it better indicates that some state-change might
  happen on the server side (i.e. the update of the history);
- a `GET` to `/anagram-utils/text` where `text` is the subject. The response body could then return a list of known
  anagrams.

Since some state knowledge is requested for implementing the history check, I took advantage of the `HistoryService`
to use such state knowledge in order to avoid re-executing the two-way check, if possible.

The data structure backing the history is a simple `HashMap`, which of course comes with limitations both in terms
of reliability and space, due to the storage in RAM. A fancier implementation could involve some kind of persistence.

## Code formatting

The code is formatted via [`spotless`](https://github.com/diffplug/spotless) according to the [`palantir`](https://github.com/diffplug/spotless/tree/main/plugin-maven#palantir-java-format) formatting rules.
