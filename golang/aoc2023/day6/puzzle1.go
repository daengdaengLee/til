package day6

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day6", "input")
	records := parseRecords(lines)

	result := 1
	for _, record := range records {
		result *= calculateWayCount(record)
	}

	fmt.Println("Day6 Puzzle1")
	fmt.Println("result=", result)
}

func calculateWayCount(record record) int {
	count := 0
	for a := 1; a <= record.time; a += 1 {
		b := record.time - a
		d := a * b
		if d > record.distance {
			count += 1
		}
	}
	return count
}
