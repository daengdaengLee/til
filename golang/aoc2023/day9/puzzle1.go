package day9

import (
	"aoc2023/common"
	"errors"
	"fmt"
)

func SolvePuzzle1() {
	lines := mustParseLines(common.ReadFileToLines("day9", "input"))

	sum := 0
	for _, line := range lines {
		nextValue := mustFindNextValue(line)
		sum += nextValue
	}

	fmt.Println("Day9 Puzzle1")
	fmt.Println("sum=", sum)
}

func mustFindNextValue(history []int) int {
	nextValue, err := findNextValue(history)
	if err != nil {
		panic(err)
	}
	return nextValue
}

func findNextValue(history []int) (int, error) {
	stack := makeHistoryStack(history)
	nextValue := 0
	for len(stack) != 0 {
		top := stack[len(stack)-1]
		stack = stack[0 : len(stack)-1]

		if len(top) == 0 {
			return 0, errors.New("empty seq")
		}
		last := top[len(top)-1]
		nextValue = last + nextValue
	}
	return nextValue, nil
}
