# anagram-utils

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

This feature verifies if two texts provided are anagrams of each other.

### History check

This feature accepts an input text, and returns all the known anagrams for such input, by considering exclusively 
the inputs that have been previously supplied to the Two-way check feature.

## How to build

## How to run

## Tests?

## Implementation details and constraints

We assume input to be valid if it meets these conditions:

- the String is not empty
- the String does not exceed `MAX_LENGTH`
- the String only contains `alpha` and space characters, as defined by [Apache's commons-lang library](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isAlphaSpace(java.lang.CharSequence\))

## Areas of improvement