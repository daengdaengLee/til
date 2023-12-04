package day4

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day4", "input")
	scratchcards := newScratchcardsFromLines(lines)

	points := 0
	for _, scratchcard := range scratchcards {
		points += scratchcard.calculatePoints()
	}

	fmt.Println("Day4 Puzzle1")
	fmt.Println("points=", points)
}
