package day5

import (
	"aoc2023/common"
	"fmt"
	"math"
)

func SolvePuzzle2() {
	lines := common.ReadFileToLines("day5", "input")
	almanacSeeds := parseSeeds2(lines)
	almanacMaps := parseAlmanacMaps(lines)
	lowestLocation := math.MaxInt

	for i, almanacSeed := range almanacSeeds {
		fmt.Printf("processing %v / %v\n", i+1, len(almanacSeeds))
		for j := 0; j < almanacSeed.length; j += 1 {
			seed := almanacSeed.start + j
			location := mapSeedToLocation(almanacMaps, seed)
			if location < lowestLocation {
				lowestLocation = location
			}
		}
	}

	fmt.Println("Day5 Puzzle2")
	fmt.Println("lowest location =", lowestLocation)
}
