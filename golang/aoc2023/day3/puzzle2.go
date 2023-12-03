package day3

import "fmt"

func SolvePuzzle2() {
	engine := getEngine()
	allLocs := getAllLocs(engine)

	sum := 0
	for i, line := range engine {
		for j, s := range line {
			if !isStar(s) {
				continue
			}
			filtered1, filtered2, filtered3 := filterAllLocs(engine, allLocs, i, j)
			totalFilteredCount := len(filtered1) + len(filtered2) + len(filtered3)
			if totalFilteredCount != 2 {
				continue
			}
			mul := 1
			for _, loc := range filtered1 {
				line := engine[i-1]
				mul *= parseNum(line, loc)
			}
			for _, loc := range filtered2 {
				line := engine[i]
				mul *= parseNum(line, loc)
			}
			for _, loc := range filtered3 {
				line := engine[i+1]
				mul *= parseNum(line, loc)
			}
			sum += mul
		}
	}

	fmt.Println("Day3 Puzzle2")
	fmt.Println("sum=", sum)
}

func filterAllLocs(engine [][]string, allLocs [][][]int, i int, j int) ([][]int, [][]int, [][]int) {
	var filtered1 [][]int
	var filtered2 [][]int
	var filtered3 [][]int

	indexes1, indexes2, indexes3 := findAllSurroundIndex(engine, i, j)
	if len(indexes1) > 0 {
		filtered1 = filterLocs(allLocs[i-1], indexes1)
	}
	if len(indexes2) > 0 {
		filtered2 = filterLocs(allLocs[i], indexes2)
	}
	if len(indexes3) > 0 {
		filtered3 = filterLocs(allLocs[i+1], indexes3)
	}

	return filtered1, filtered2, filtered3
}

func filterLocs(locs [][]int, indexes []int) [][]int {
	var filtered [][]int

	for _, loc := range locs {
		for _, idx := range indexes {
			if isMatchedLoc(loc, idx) {
				filtered = append(filtered, loc)
				break
			}
		}
	}

	return filtered
}

func isMatchedLoc(loc []int, index int) bool {
	return loc[0] <= index && loc[1] >= index
}

func findAllSurroundIndex(engine [][]string, i int, j int) ([]int, []int, []int) {
	var indexes1 []int
	var indexes2 []int
	var indexes3 []int

	line := engine[i]

	startJ := j - 1
	if startJ < 0 {
		startJ = 0
	}
	if startJ != j {
		indexes2 = append(indexes2, startJ)
	}

	endJ := j + 1
	if endJ >= len(line) {
		endJ = len(line) - 1
	}
	if endJ != j {
		indexes2 = append(indexes2, endJ)
	}

	if i > 0 {
		indexes1 = []int{startJ, j, endJ}
	}

	if i < len(line)-1 {
		indexes3 = []int{startJ, j, endJ}
	}

	return indexes1, indexes2, indexes3
}

func isStar(s string) bool {
	return s == "*"
}
