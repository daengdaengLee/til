package day10

import (
	"aoc2023/common"
	"errors"
	"fmt"
	"slices"
)

func SolvePuzzle2() {
	tiles := common.ReadFileToSplitLines("day10", "input")
	edges := makeEdges(tiles)
	loop := findLoop(tiles, edges)
	allInnerPositions := findInnerPositions(loop)

	fmt.Println("Day10 Puzzle2")
	fmt.Println("count=", len(allInnerPositions))
}

func findInnerPositions(loop []position) []position {
	if len(loop) == 0 {
		panic(errors.New("no loop"))
	}

	// loop 의 좌상단 위치 탐색
	startPositionIndex := 0
	for i := 1; i < len(loop); i += 1 {
		startPosition := loop[startPositionIndex]
		position := loop[i]
		if position.rowIdx > startPosition.rowIdx {
			continue
		}
		if position.rowIdx < startPosition.rowIdx {
			startPositionIndex = i
			continue
		}
		if position.colIdx < startPosition.colIdx {
			startPositionIndex = i
			continue
		}
	}

	rightDelta := position{rowIdx: 0, colIdx: 1}
	leftDelta := position{rowIdx: 0, colIdx: -1}
	upDelta := position{rowIdx: -1, colIdx: 0}
	downDelta := position{rowIdx: 1, colIdx: 0}
	innerPositionSet := make(map[position]bool)
	innerDirectionDelta := rightDelta
	var subInnerDirectionDelta *position = nil
	for i := 0; i < len(loop); i += 1 {
		currentIndex := (startPositionIndex + i) % len(loop)
		currentPosition := loop[currentIndex]

		prevIndex := (currentIndex - 1 + len(loop)) % len(loop)
		prevPosition := loop[prevIndex]

		nextIndex := (currentIndex + 1) % len(loop)
		nextPosition := loop[nextIndex]

		moveDirection1 := findMoveDirection(prevPosition, currentPosition)
		moveDirection2 := findMoveDirection(currentPosition, nextPosition)
		switch moveDirection1 {
		case "right":
			switch moveDirection2 {
			case "right":
				// keep going
				subInnerDirectionDelta = nil
			case "left":
				panic(errors.New(fmt.Sprintf("invalid move direction change: %+v -> %+v", moveDirection1, moveDirection2)))
			case "up":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case upDelta:
					innerDirectionDelta = leftDelta
				case downDelta:
					innerDirectionDelta = rightDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "down":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case upDelta:
					innerDirectionDelta = rightDelta
				case downDelta:
					innerDirectionDelta = leftDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			}
		case "left":
			switch moveDirection2 {
			case "right":
				panic(errors.New(fmt.Sprintf("invalid move direction change: %+v -> %+v", moveDirection1, moveDirection2)))
			case "left":
				// keep going
				subInnerDirectionDelta = nil
			case "up":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case upDelta:
					innerDirectionDelta = rightDelta
				case downDelta:
					innerDirectionDelta = leftDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "down":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case upDelta:
					innerDirectionDelta = leftDelta
				case downDelta:
					innerDirectionDelta = rightDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			}
		case "up":
			switch moveDirection2 {
			case "right":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case rightDelta:
					innerDirectionDelta = downDelta
				case leftDelta:
					innerDirectionDelta = upDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "left":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case rightDelta:
					innerDirectionDelta = upDelta
				case leftDelta:
					innerDirectionDelta = downDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "up":
				// keep going
				subInnerDirectionDelta = nil
			case "down":
				panic(errors.New(fmt.Sprintf("invalid move direction change: %+v -> %+v", moveDirection1, moveDirection2)))
			}
		case "down":
			switch moveDirection2 {
			case "right":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case rightDelta:
					innerDirectionDelta = upDelta
				case leftDelta:
					innerDirectionDelta = downDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "left":
				subInnerDirectionDelta = &position{
					rowIdx: innerDirectionDelta.rowIdx,
					colIdx: innerDirectionDelta.colIdx,
				}
				switch innerDirectionDelta {
				case rightDelta:
					innerDirectionDelta = downDelta
				case leftDelta:
					innerDirectionDelta = upDelta
				default:
					panic(errors.New(fmt.Sprintf("invalid inner direction with move direction change: %+v with %+v -> %+v", innerDirectionDelta, moveDirection1, moveDirection2)))
				}
			case "up":
				panic(errors.New(fmt.Sprintf("invalid move direction change: %+v -> %+v", moveDirection1, moveDirection2)))
			case "down":
				// keep going
				subInnerDirectionDelta = nil
			}
		}

		innerPosition := position{
			rowIdx: currentPosition.rowIdx + innerDirectionDelta.rowIdx,
			colIdx: currentPosition.colIdx + innerDirectionDelta.colIdx,
		}
		for !slices.Contains(loop, innerPosition) {
			innerPositionSet[innerPosition] = true
			innerPosition = position{
				rowIdx: innerPosition.rowIdx + innerDirectionDelta.rowIdx,
				colIdx: innerPosition.colIdx + innerDirectionDelta.colIdx,
			}
		}

		if subInnerDirectionDelta != nil {
			innerPosition := position{
				rowIdx: currentPosition.rowIdx + subInnerDirectionDelta.rowIdx,
				colIdx: currentPosition.colIdx + subInnerDirectionDelta.colIdx,
			}
			for !slices.Contains(loop, innerPosition) {
				innerPositionSet[innerPosition] = true
				innerPosition = position{
					rowIdx: innerPosition.rowIdx + subInnerDirectionDelta.rowIdx,
					colIdx: innerPosition.colIdx + subInnerDirectionDelta.colIdx,
				}
			}
		}
	}

	innerPositions := make([]position, 0, len(innerPositionSet))
	for position := range innerPositionSet {
		innerPositions = append(innerPositions, position)
	}
	return innerPositions
}

func findMoveDirection(position1 position, position2 position) string {
	if position1.rowIdx == position2.rowIdx {
		if position1.colIdx < position2.colIdx {
			return "right"
		} else if position1.colIdx > position2.colIdx {
			return "left"
		}
	} else if position1.colIdx == position2.colIdx {
		if position1.rowIdx < position2.rowIdx {
			return "down"
		} else if position1.rowIdx > position2.rowIdx {
			return "up"
		}
	}

	panic(errors.New(fmt.Sprintf("invalid movement: %+v -> %+v", position1, position2)))
}
