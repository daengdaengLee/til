package day10

import (
	"aoc2023/common"
	"errors"
	"fmt"
)

func SolvePuzzle1() {
	tiles := common.ReadFileToSplitLines("day10", "input")
	edges := makeEdges(tiles)
	loop := findLoop(tiles, edges)
	steps := countSteps(loop)
	maxStep := maxInt(steps)

	fmt.Println("Day10 Puzzle1")
	fmt.Println("maxStep=", maxStep)
}

func countSteps(loop []position) []int {
	if len(loop) < 2 {
		panic(errors.New("too short loop"))
	}
	steps := make([]int, len(loop))
	step := 1
	i := 1
	j := len(steps) - 1
	for i <= j {
		steps[i] = step
		steps[j] = step
		step += 1
		i += 1
		j -= 1
	}
	return steps
}

func maxInt(nums []int) int {
	if len(nums) == 0 {
		return 0
	}
	n := nums[0]
	for _, m := range nums[1:] {
		if m > n {
			n = m
		}
	}
	return n
}
