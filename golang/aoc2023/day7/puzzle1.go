package day7

import (
	"aoc2023/common"
	"fmt"
	"sort"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day7", "input")
	handAndBids := parseToHandAndBids(lines)
	sort.SliceStable(handAndBids, func(i int, j int) bool {
		handAndBidA := handAndBids[i]
		handAndBidB := handAndBids[j]

		handTypeCompare := compareHandType(handAndBidA.hand.handType, handAndBidB.hand.handType)
		if handTypeCompare < 0 {
			return true
		} else if handTypeCompare > 0 {
			return false
		}

		cardsCompare := compareCards(handAndBidA.hand.cards, handAndBidB.hand.cards)
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

	fmt.Println("Day7 Puzzle1")
	fmt.Println("sum=", sum)
}
