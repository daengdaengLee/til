package day4

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle2() {
	lines := common.ReadFileToLines("day4", "input")
	scratchcards := newScratchcardsFromLines(lines)

	total := 0
	for i, scratchcard := range scratchcards {
		matchingCounts := scratchcard.calculateMatchingCounts()
		for j := 1; j <= matchingCounts; j += 1 {
			k := i + j
			if k >= len(scratchcards) {
				break
			}
			scratchcards[k].increaseCardCount(scratchcard.cardCount)
		}
		total += scratchcard.cardCount
	}

	fmt.Println("Day4 Puzzle2")
	fmt.Println("total=", total)
}
