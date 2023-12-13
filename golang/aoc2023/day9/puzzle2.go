package day9

import (
	"aoc2023/common"
	"errors"
	"fmt"
)

func SolvePuzzle2() {
	lines := mustParseLines(common.ReadFileToLines("day9", "input"))

	sum := 0
	for _, line := range lines {
		prevValue := mustFindPrevValue(line)
		sum += prevValue
	}

	fmt.Println("Day9 Puzzle2")
	fmt.Println("sum=", sum)
}

func mustFindPrevValue(history []int) int {
	prevValue, err := findPrevValue(history)
	if err != nil {
		panic(err)
	}
	return prevValue
}

func findPrevValue(history []int) (int, error) {
	stack := makeHistoryStack(history)
	prevValue := 0
	for len(stack) != 0 {
		top := stack[len(stack)-1]
		stack = stack[0 : len(stack)-1]

		if len(top) == 0 {
			return 0, errors.New("empty seq")
		}
		first := top[0]
		prevValue = first - prevValue
	}
	return prevValue, nil
}
