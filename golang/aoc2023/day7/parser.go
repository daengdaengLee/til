package day7

import (
	"strconv"
	"strings"
)

func parseToHandAndBid(line string) handAndBid {
	tokens := strings.Split(line, " ")
	cards := strings.Split(tokens[0], "")
	handType := calculateHandType(cards)
	bid, err := strconv.Atoi(tokens[1])
	if err != nil {
		panic(err)
	}
	return handAndBid{hand: hand{cards: cards, handType: handType}, bid: bid}
}

func parseToHandAndBids(lines []string) []handAndBid {
	handAndBids := make([]handAndBid, len(lines))
	for i, line := range lines {
		handAndBids[i] = parseToHandAndBid(line)
	}
	return handAndBids
}
