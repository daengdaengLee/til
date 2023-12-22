package part1

import (
	"aoc2023/common"
	"fmt"
)

func Solve() {
	lines := common.ReadFileToSplitLines("day13", "input")
	patterns := groupPatterns(lines)
	answer := 0
	for _, pattern := range patterns {
		vertical := findVerticalReflection(pattern)
		if vertical > -1 {
			answer += vertical + 1
			continue
		}

		horizontal := findHorizontalReflection(pattern)
		if horizontal > -1 {
			answer += (horizontal + 1) * 100
		}
	}
	fmt.Printf("Day13 Part1: %+v\n", answer)
}

func groupPatterns(lines [][]string) [][][]string {
	var patterns [][][]string

	var pattern [][]string
	for _, line := range lines {
		if (len(line) == 0) && (len(pattern) > 0) {
			patterns = append(patterns, pattern)
			pattern = make([][]string, 0)
		} else {
			pattern = append(pattern, line)
		}
	}
	if len(pattern) > 0 {
		patterns = append(patterns, pattern)
	}

	return patterns
}

func findHorizontalReflection(pattern [][]string) int {
	for i := 0; i < len(pattern)-1; i += 1 {
		top := pattern[0 : i+1]
		bottom := pattern[i+1:]

		topN := len(top)
		bottomN := len(bottom)
		n := min(topN, bottomN)

		top = top[len(top)-n:]
		bottom = bottom[0:n]
		if isReflection(top, bottom) {
			return i
		}
	}
	return -1
}

func findVerticalReflection(pattern [][]string) int {
	tPattern := transpose(pattern)
	return findHorizontalReflection(tPattern)
}

func isReflection(patternA [][]string, patternB [][]string) bool {
	if len(patternA) != len(patternB) {
		return false
	}

	l := len(patternA)
	for i := 0; i < l; i += 1 {
		lineA := patternA[i]
		lineB := patternB[l-i-1]

		if len(lineA) != len(lineB) {
			return false
		}

		for j := 0; j < len(lineA); j += 1 {
			a := lineA[j]
			b := lineB[j]
			if a != b {
				return false
			}
		}
	}

	return true
}

func transpose(pattern [][]string) [][]string {
	rows := len(pattern)
	if rows == 0 {
		return pattern
	}
	columns := len(pattern[0])

	t := make([][]string, 0, columns)
	for j := 0; j < columns; j += 1 {
		tRow := make([]string, rows)
		for i := 0; i < rows; i += 1 {
			tRow[i] = pattern[i][j]
		}
		t = append(t, tRow)
	}

	return t
}
