package day6

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle2() {
	lines := common.ReadFileToLines("day6", "input")
	record := parseRecords2(lines)
	result := calculateWayCount(record)

	fmt.Println("Day6 Puzzle2")
	fmt.Println("result=", result)
}
