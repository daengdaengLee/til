package day7

import (
	"errors"
	"sort"
)

type handAndBid struct {
	hand []string
	bid  int
}

func calculateHandType1(cards []string) string {
	m := makeCountByCard(cards)

	if isFiveOfAKind(m) {
		return "FiveOfAKind"
	}
	if isFourOfAKind(m) {
		return "FourOfAKind"
	}
	if isFullHouse(m) {
		return "FullHouse"
	}
	if isThreeOfAKind(m) {
		return "ThreeOfAKind"
	}
	if isTwoPair(m) {
		return "TwoPair"
	}
	if isOnePair(m) {
		return "OnePair"
	}
	if isHighCard(m) {
		return "HighCard"
	}
	panic(errors.New("no matched hand type"))
}

func calculateHandType2(cards []string) string {
	m := makeCountByCard(cards)

	if isFiveOfAKind(m) || isFiveOfAKindWithJ(m) {
		return "FiveOfAKind"
	}
	if isFourOfAKind(m) || isFourOfAKindWithJ(m) {
		return "FourOfAKind"
	}
	if isFullHouse(m) || isFullHouseWithJ(m) {
		return "FullHouse"
	}
	if isThreeOfAKind(m) || isThreeOfAKindWithJ(m) {
		return "ThreeOfAKind"
	}
	if isTwoPair(m) || isTwoPairWithJ(m) {
		return "TwoPair"
	}
	if isOnePair(m) || isOnePairWithJ(m) {
		return "OnePair"
	}
	if isHighCard(m) {
		return "HighCard"
	}
	panic(errors.New("no matched hand type"))
}

func isFiveOfAKind(cards map[string]int) bool {
	return len(cards) == 1
}

func isFiveOfAKindWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	return isJ && len(cards) == 2
}

func isFourOfAKind(cards map[string]int) bool {
	vs := values(cards)
	sort.Ints(vs)
	return len(vs) == 2 && vs[0] == 1 && vs[1] == 4
}

func isFourOfAKindWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	if !isJ {
		return false
	}
	cardsWithoutJ := copyMap(cards)
	delete(cardsWithoutJ, "J")
	vs := values(cardsWithoutJ)
	sort.Ints(vs)
	return len(vs) == 2 && vs[0] == 1
}

func isFullHouse(cards map[string]int) bool {
	vs := values(cards)
	sort.Ints(vs)
	return len(vs) == 2 && vs[0] == 2 && vs[1] == 3
}

func isFullHouseWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	return isJ && len(cards) == 3
}

func isThreeOfAKind(cards map[string]int) bool {
	vs := values[string, int](cards)
	sort.Ints(vs)
	return len(vs) == 3 && vs[0] == 1 && vs[1] == 1 && vs[2] == 3
}

func isThreeOfAKindWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	return isJ && len(cards) == 4
}

func isTwoPair(cards map[string]int) bool {
	vs := values[string, int](cards)
	sort.Ints(vs)
	return len(vs) == 3 && vs[0] == 1 && vs[1] == 2 && vs[2] == 2
}

func isTwoPairWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	return isJ && len(cards) == 4
}

func isOnePair(cards map[string]int) bool {
	vs := values[string, int](cards)
	sort.Ints(vs)
	return len(vs) == 4 && vs[0] == 1 && vs[1] == 1 && vs[2] == 1 && vs[3] == 2
}

func isOnePairWithJ(cards map[string]int) bool {
	_, isJ := cards["J"]
	return isJ && len(cards) == 5
}

func isHighCard(cards map[string]int) bool {
	return len(cards) == 5
}

func makeCountByCard(cards []string) map[string]int {
	m := make(map[string]int)
	for _, card := range cards {
		v, ok := m[card]
		if ok {
			m[card] = v + 1
		} else {
			m[card] = 1
		}
	}
	return m
}

func values[K comparable, V any](m map[K]V) []V {
	vs := make([]V, 0, len(m))
	for _, v := range m {
		vs = append(vs, v)
	}
	return vs
}

func copyMap[K comparable, V any](m map[K]V) map[K]V {
	c := make(map[K]V, len(m))
	for k, v := range m {
		c[k] = v
	}
	return c
}

func mapHandTypeToScore(handType string) int {
	switch handType {
	case "FiveOfAKind":
		return 7
	case "FourOfAKind":
		return 6
	case "FullHouse":
		return 5
	case "ThreeOfAKind":
		return 4
	case "TwoPair":
		return 3
	case "OnePair":
		return 2
	case "HighCard":
		return 1
	default:
		return 0
	}
}

func compareHandType(handTypeA string, handTypeB string) int {
	scoreA := mapHandTypeToScore(handTypeA)
	scoreB := mapHandTypeToScore(handTypeB)
	return scoreA - scoreB
}

func mapCardToScore1(card string) int {
	switch card {
	case "A":
		return 13
	case "K":
		return 12
	case "Q":
		return 11
	case "J":
		return 10
	case "T":
		return 9
	case "9":
		return 8
	case "8":
		return 7
	case "7":
		return 6
	case "6":
		return 5
	case "5":
		return 4
	case "4":
		return 3
	case "3":
		return 2
	case "2":
		return 1
	default:
		return 0
	}
}

func mapCardToScore2(card string) int {
	switch card {
	case "A":
		return 13
	case "K":
		return 12
	case "Q":
		return 11
	case "T":
		return 10
	case "9":
		return 9
	case "8":
		return 8
	case "7":
		return 7
	case "6":
		return 6
	case "5":
		return 5
	case "4":
		return 4
	case "3":
		return 3
	case "2":
		return 2
	case "J":
		return 1
	default:
		return 0
	}
}

func compareCards(cardsA []string, cardsB []string, f func(string) int) int {
	l := len(cardsA)
	for i := 0; i < l; i += 1 {
		cardA := cardsA[i]
		cardB := cardsB[i]

		scoreA := f(cardA)
		scoreB := f(cardB)

		result := scoreA - scoreB
		if result != 0 {
			return result
		}
	}
	return 0
}
