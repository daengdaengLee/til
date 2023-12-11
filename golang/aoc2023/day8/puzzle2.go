package day8

import (
	"aoc2023/common"
	"fmt"
	"strings"
)

func SolvePuzzle2() {
	lines := common.ReadFileToLines("day8", "input")
	instructions := parseInstructions(lines[0])
	nodeMap := parseNodeMap(lines[2:])

	nodes := filterStartNodes(nodeMap)
	counts := make([]int, len(nodes))
	for i, node := range nodes {
		count := calculateCountOfNode(node, instructions, nodeMap)
		counts[i] = count
	}
	count := 0
	if len(counts) == 0 {
		panic("no counts")
	} else if len(counts) == 1 {
		count = counts[0]
	} else {
		count = lcm(counts[0], counts[1])
		for _, anotherCount := range counts[2:] {
			count = lcm(count, anotherCount)
		}
	}

	fmt.Println("Day8 Puzzle2")
	fmt.Println("count=", count)
}

func filterStartNodes(nodeMap map[string][]string) []string {
	var nodes []string
	for node := range nodeMap {
		if strings.HasSuffix(node, "A") {
			nodes = append(nodes, node)
		}
	}
	return nodes
}

func calculateCountOfNode(node string, instructions []string, nodeMap map[string][]string) int {
	ii := newInfiniteInstructions(instructions)
	count := 0
	for !strings.HasSuffix(node, "Z") {
		count += 1
		instruction := ii.nextInstruction()
		node = mustGetNextNode(node, instruction, nodeMap)
	}
	return count
}

func gcd(a, b int) int {
	for b != 0 {
		t := b
		b = a % b
		a = t
	}
	return a
}

func lcm(a, b int) int {
	return (a * b) / gcd(a, b)
}
