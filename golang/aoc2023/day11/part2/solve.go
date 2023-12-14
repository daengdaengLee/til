package part2

import (
	"aoc2023/common"
	"fmt"
)

func Solve() {
	space := common.ReadFileToSplitLines("day11", "input")
	galaxyPositions := getGalaxyPositions(space)
	galaxyPositionPairs := makePairs(galaxyPositions)

	galaxyRowIndexSet := make(map[int]int)
	galaxyColIndexSet := make(map[int]int)
	for _, galaxyPosition := range galaxyPositions {
		galaxyRowIndexSet[galaxyPosition[0]] = galaxyPosition[0]
		galaxyColIndexSet[galaxyPosition[1]] = galaxyPosition[1]
	}

	expandRatio := 1000000
	shortestPathSteps := 0
	for _, galaxyPositionPair := range galaxyPositionPairs {
		galaxyPositionA := galaxyPositionPair[0]
		galaxyPositionB := galaxyPositionPair[1]

		rowIndexUp := 0
		rowIndexDown := 0
		if galaxyPositionA[0] < galaxyPositionB[0] {
			rowIndexUp = galaxyPositionA[0]
			rowIndexDown = galaxyPositionB[0]
		} else {
			rowIndexUp = galaxyPositionB[0]
			rowIndexDown = galaxyPositionA[0]
		}

		emptyRowCount := 0
		for rowIndex := rowIndexUp; rowIndex < rowIndexDown; rowIndex += 1 {
			_, isGalaxyRow := galaxyRowIndexSet[rowIndex]
			if !isGalaxyRow {
				emptyRowCount += 1
			}
		}

		rowDiff := ((rowIndexDown - rowIndexUp) - emptyRowCount) + (emptyRowCount * expandRatio)

		colIndexLeft := 0
		colIndexRight := 0
		if galaxyPositionA[1] < galaxyPositionB[1] {
			colIndexLeft = galaxyPositionA[1]
			colIndexRight = galaxyPositionB[1]
		} else {
			colIndexLeft = galaxyPositionB[1]
			colIndexRight = galaxyPositionA[1]
		}

		emptyColCount := 0
		for colIndex := colIndexLeft; colIndex < colIndexRight; colIndex += 1 {
			_, isGalaxyCol := galaxyColIndexSet[colIndex]
			if !isGalaxyCol {
				emptyColCount += 1
			}
		}

		colDiff := ((colIndexRight - colIndexLeft) - emptyColCount) + (emptyColCount * expandRatio)

		steps := rowDiff + colDiff
		shortestPathSteps += steps
	}

	fmt.Println("Day11 Puzzle2")
	fmt.Println("steps=", shortestPathSteps)
}

func makePairs[T any](coll []T) [][]T {
	pairs := make([][]T, 0, (len(coll)*(len(coll)-1))/2)
	for i := 0; i < len(coll)-1; i += 1 {
		for j := i + 1; j < len(coll); j += 1 {
			pair := []T{coll[i], coll[j]}
			pairs = append(pairs, pair)
		}
	}
	return pairs
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
