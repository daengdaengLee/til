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

	locationCh := make(chan int)
	count := 0
	for _, seed := range almanacSeeds {
		count += 1
		fmt.Printf("run %v/%v...\n", count, len(almanacSeeds))
		go func(locationCh chan<- int, almanacSeed almanacSeed, count int) {
			fmt.Printf("processing %v/%v...\n", count, len(almanacSeeds))
			lowestLocation := math.MaxInt
			for j := 0; j < almanacSeed.length; j += 1 {
				seed := almanacSeed.start + j
				location := mapSeedToLocation(almanacMaps, seed)
				if location < lowestLocation {
					lowestLocation = location
				}
			}
			locationCh <- lowestLocation
		}(locationCh, seed, count)
	}

	for count > 0 {
		location := <-locationCh
		count -= 1
		fmt.Printf("done! remains %v/%v\n", count, len(almanacSeeds))
		if location < lowestLocation {
			lowestLocation = location
		}
	}

	fmt.Println("Day5 Puzzle2")
	fmt.Println("lowest location =", lowestLocation)
}
