package day8

import (
	"aoc2023/common"
	"fmt"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day8", "input")
	instructions := newInfiniteInstructions(parseInstructions(lines[0]))
	nodeMap := parseNodeMap(lines[2:])

	count := 0
	node := "AAA"
	for node != "ZZZ" {
		count += 1
		instruction := instructions.nextInstruction()
		node = mustGetNextNode(node, instruction, nodeMap)
	}

	fmt.Println("Day8 Puzzle1")
	fmt.Println("count=", count)
}
