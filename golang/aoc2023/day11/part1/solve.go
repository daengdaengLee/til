package part1

import (
	"aoc2023/common"
	"errors"
	"fmt"
)

func Solve() {
	space := common.ReadFileToSplitLines("day11", "input")
	expandedSpace := expandSpace(space)
	galaxyPositions := getGalaxyPositions(expandedSpace)

	galaxyPositionPairs := make([][][]int, 0, (len(galaxyPositions)*(len(galaxyPositions)-1))/2)
	for i := 0; i < len(galaxyPositions)-1; i += 1 {
		for j := i + 1; j < len(galaxyPositions); j += 1 {
			galaxyPositionPair := [][]int{galaxyPositions[i], galaxyPositions[j]}
			galaxyPositionPairs = append(galaxyPositionPairs, galaxyPositionPair)
		}
	}

	shortestPathSteps := 0
	for _, galaxyPositionPair := range galaxyPositionPairs {
		galaxyPositionA := galaxyPositionPair[0]
		galaxyPositionB := galaxyPositionPair[1]

		rowDiff := absInt(galaxyPositionA[0] - galaxyPositionB[0])
		colDiff := absInt(galaxyPositionA[1] - galaxyPositionB[1])

		steps := rowDiff + colDiff
		shortestPathSteps += steps
	}

	fmt.Println("Day11 Puzzle1")
	fmt.Println("steps=", shortestPathSteps)
}

func absInt(v int) int {
	if v < 0 {
		return -v
	} else {
		return v
	}
}

func getSpaceSize(space [][]string) []int {
	rowCount := len(space)
	if rowCount == 0 {
		return []int{0, 0}
	}
	colCount := 0
	colCountSet := make(map[int]int)
	for _, row := range space {
		colCount = len(row)
		colCountSet[colCount] = colCount
	}
	if len(colCountSet) != 1 {
		panic(errors.New("invalid space, some rows have different length"))
	}
	return []int{rowCount, colCount}
}

func getGalaxyPositions(space [][]string) [][]int {
	var galaxyPositions [][]int
	for rowIndex, row := range space {
		for colIndex, token := range row {
			if token == "#" {
				galaxyPositions = append(galaxyPositions, []int{rowIndex, colIndex})
			}
		}
	}
	return galaxyPositions
}

func expandSpace(space [][]string) [][]string {
	spaceSize := getSpaceSize(space)
	galaxyPositions := getGalaxyPositions(space)

	galaxyRowIndexSet := make(map[int]int)
	galaxyColIndexSet := make(map[int]int)
	for _, galaxyPosition := range galaxyPositions {
		galaxyRowIndexSet[galaxyPosition[0]] = galaxyPosition[0]
		galaxyColIndexSet[galaxyPosition[1]] = galaxyPosition[1]
	}
	galaxyRowCount := len(galaxyRowIndexSet)
	galaxyColCount := len(galaxyColIndexSet)

	expandedSpace := make([][]string, 0, (spaceSize[0]*2)-galaxyRowCount)
	for rowIndex, row := range space {
		_, isGalaxyRow := galaxyRowIndexSet[rowIndex]

		expandedRow := make([]string, 0, (spaceSize[1]*2)-galaxyColCount)
		for colIndex, token := range row {
			_, isGalaxyCol := galaxyColIndexSet[colIndex]

			if isGalaxyCol {
				expandedRow = append(expandedRow, token)
			} else {
				expandedRow = append(expandedRow, token, token)
			}
		}

		if isGalaxyRow {
			expandedSpace = append(expandedSpace, expandedRow)
		} else {
			expandedSpace = append(expandedSpace, expandedRow, expandedRow)
		}
	}
	return expandedSpace
}
