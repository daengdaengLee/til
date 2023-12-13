package day10

import (
	"errors"
	"fmt"
	"slices"
)

const startTile = "S"

type position struct {
	rowIdx int
	colIdx int
}

type tiles = [][]string

type edges = map[position][]position

type deltasByTile = map[string][]position

func findStartTilePosition(tiles [][]string) position {
	for i, row := range tiles {
		for j, tile := range row {
			if tile == startTile {
				return position{rowIdx: i, colIdx: j}
			}
		}
	}
	panic(errors.New("no start tile"))
}

func makeDeltasByTile() deltasByTile {
	return deltasByTile{
		"|": []position{{rowIdx: -1, colIdx: 0}, {rowIdx: 1, colIdx: 0}},
		"-": []position{{rowIdx: 0, colIdx: -1}, {rowIdx: 0, colIdx: 1}},
		"L": []position{{rowIdx: -1, colIdx: 0}, {rowIdx: 0, colIdx: 1}},
		"J": []position{{rowIdx: -1, colIdx: 0}, {rowIdx: 0, colIdx: -1}},
		"7": []position{{rowIdx: 1, colIdx: 0}, {rowIdx: 0, colIdx: -1}},
		"F": []position{{rowIdx: 1, colIdx: 0}, {rowIdx: 0, colIdx: 1}},
		".": []position{},
	}
}

func makeEdges(tiles tiles) edges {
	rowCount := len(tiles)
	if rowCount == 0 {
		panic(errors.New("empty tiles, no rows"))
	}
	colCount := len(tiles[0])
	if colCount == 0 {
		panic(errors.New("empty tiles, no cols"))
	}

	deltasByTile := makeDeltasByTile()
	edges := make(edges)

	for i, row := range tiles {
		for j, tile := range row {
			deltas, ok := deltasByTile[tile]
			if !ok {
				continue
			}
			neighborPositions := make([]position, 0, len(deltas))
			for _, delta := range deltas {
				neighborPosition := position{rowIdx: i + delta.rowIdx, colIdx: j + delta.colIdx}
				if (neighborPosition.rowIdx >= 0 || neighborPosition.rowIdx < rowCount) &&
					(neighborPosition.colIdx >= 0 || neighborPosition.colIdx < colCount) {
					neighborPositions = append(neighborPositions, neighborPosition)
				}
			}
			edges[position{rowIdx: i, colIdx: j}] = neighborPositions
		}
	}

	startTilePosition := findStartTilePosition(tiles)
	startTileNeighborPositions := make([]position, 0, 2)
	for _, delta := range []position{
		{rowIdx: -1, colIdx: 0},
		{rowIdx: 1, colIdx: 0},
		{rowIdx: 0, colIdx: -1},
		{rowIdx: 0, colIdx: 1},
	} {
		neighborPosition := position{
			rowIdx: startTilePosition.rowIdx + delta.rowIdx,
			colIdx: startTilePosition.colIdx + delta.colIdx,
		}
		if (neighborPosition.rowIdx < 0) ||
			(neighborPosition.rowIdx >= rowCount) ||
			(neighborPosition.colIdx < 0) ||
			(neighborPosition.colIdx >= colCount) {
			continue
		}
		neighborNeighborPositions, ok := edges[neighborPosition]
		if !ok {
			panic(errors.New("no edge"))
		}
		if slices.Contains(neighborNeighborPositions, startTilePosition) {
			startTileNeighborPositions = append(startTileNeighborPositions, neighborPosition)
		}
	}
	edges[startTilePosition] = startTileNeighborPositions

	return edges
}

func findLoop(tiles tiles, edges edges) []position {
	startTilePosition := findStartTilePosition(tiles)
	startTileNeighborPositions, ok := edges[startTilePosition]
	if !ok || (len(startTileNeighborPositions) == 0) {
		panic(errors.New("no loop"))
	}
	startTileNextPosition := startTileNeighborPositions[0]
	loop := []position{startTilePosition, startTileNextPosition}
	for loop[0] != loop[len(loop)-1] {
		prevPosition := loop[len(loop)-2]
		currentPosition := loop[len(loop)-1]

		neighborPositions, ok := edges[currentPosition]
		if !ok || (len(neighborPositions) == 0) {
			panic(errors.New(fmt.Sprintf("no loop: %+v", currentPosition)))
		}

		nextPosition := neighborPositions[0]
		if nextPosition == prevPosition {
			nextPosition = neighborPositions[1]
		}

		loop = append(loop, nextPosition)
	}
	return loop[0 : len(loop)-1]
}
