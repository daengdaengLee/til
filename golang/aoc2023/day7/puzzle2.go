package day7

import (
	"aoc2023/common"
	"fmt"
	"sort"
)

func SolvePuzzle2() {
	lines := common.ReadFileToLines("day7", "input")
	handAndBids := parseToHandAndBids(lines)
	sort.SliceStable(handAndBids, func(i int, j int) bool {
		handAndBidA := handAndBids[i]
		handAndBidB := handAndBids[j]

		handTypeA := calculateHandType2(handAndBidA.hand)
		handTypeB := calculateHandType2(handAndBidB.hand)
		handTypeCompare := compareHandType(handTypeA, handTypeB)
		if handTypeCompare < 0 {
			return true
		} else if handTypeCompare > 0 {
			return false
		}

		cardsCompare := compareCards(
			handAndBidA.hand,
			handAndBidB.hand,
			mapCardToScore2)
		if cardsCompare < 0 {
			return true
		} else if cardsCompare > 0 {
			return false
		}

		return handAndBidA.bid < handAndBidB.bid
	})
	sum := 0
	for i, handAndBid := range handAndBids {
		rank := i + 1
		sum += rank * handAndBid.bid
	}

	fmt.Println("Day7 Puzzle2")
	fmt.Println("sum=", sum)
}
