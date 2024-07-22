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

`docker build -f Dockerfile -t anagram-utils && docker run -it anagram-utils`

## Observations

We assume an input text to be valid if it meets the following conditions:

- it is not empty
- it does not exceed `MAX_LENGTH`
- it only contains `alpha` and space characters, as defined
  by [Apache's commons-lang library](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isAlphaSpace(java.lang.CharSequence\))

A `Text` acts as a wrapper for inputs that are valid according to the aforementioned definition, i.e. no
`Text`s wrapping invalid inputs can be created.

Since some state knowledge is requested for implementing the history check, I took advantage of the `HistoryService`
to use such state knowledge in order to avoid re-executing the two-way check, if possible.

For example, requesting a computation-intensive two-way check on the same inputs twice (or more) in a row, will only
cause the algorithm to be executed the first time. The remaining times, results will be fetched from history.

The data structure backing the history is a simple `HashMap`, which of course comes with limitations both in terms
of reliability and space, due to the storage in RAM. A fancier implementation could involve some kind of persistence.
