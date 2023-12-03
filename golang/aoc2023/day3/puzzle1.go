package day3

import (
	"fmt"
)

func SolvePuzzle1() {
	engine := getEngine()
	allLocs := getAllLocs(engine)

	sum := 0
	for i, line := range engine {
		for _, loc := range allLocs[i] {
			for _, surround := range findSurrounds(engine, i, loc) {
				if isSymbol(surround) {
					sum += parseNum(line, loc)
					break
				}
			}
		}
	}
	fmt.Println("Day3 Puzzle1")
	fmt.Println("sum=", sum)
}

func findSurrounds(engine [][]string, i int, js []int) []string {
	var surrounded []string

	line := engine[i]

	startJ := js[0] - 1
	if startJ < 0 {
		startJ = 0
	}
	if startJ != js[0] {
		surrounded = append(surrounded, line[startJ])
	}

	endJ := js[1] + 1
	if endJ >= len(line) {
		endJ = len(line) - 1
	}
	if endJ != js[1] {
		surrounded = append(surrounded, line[endJ])
	}

	if i > 0 {
		line := engine[i-1]
		for j := startJ; j <= endJ; j += 1 {
			surrounded = append(surrounded, line[j])
		}
	}

	if i < len(line)-1 {
		line := engine[i+1]
		for j := startJ; j <= endJ; j += 1 {
			surrounded = append(surrounded, line[j])
		}
	}

	return surrounded
}

func isSymbol(s string) bool {
	if isDigit(s) {
		return false
	}
	return s != "."
}
