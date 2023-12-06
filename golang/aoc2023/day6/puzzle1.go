package day6

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day6", "input")
	records := parseRecords1(lines)

	result := 1
	for _, record := range records {
		result *= calculateWayCount(record)
	}

	fmt.Println("Day6 Puzzle1")
	fmt.Println("result=", result)
}
