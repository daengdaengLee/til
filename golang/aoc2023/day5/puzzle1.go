package day5

import (
	"aoc2023/common"
	"fmt"
	"math"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day5", "input")
	seeds := parseSeeds1(lines)
	almanacMaps := parseAlmanacMaps(lines)
	lowestLocation := math.MaxInt

	for _, seed := range seeds {
		location := mapSeedToLocation(almanacMaps, seed)
		if location < lowestLocation {
			lowestLocation = location
		}
	}

	fmt.Println("Day5 Puzzle1")
	fmt.Println("lowest location =", lowestLocation)
}
