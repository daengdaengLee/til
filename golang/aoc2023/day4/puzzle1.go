package day4

import (
	"aoc2023/common"
	"fmt"
	"regexp"
	"slices"
	"strconv"
	"strings"
)

func SolvePuzzle1() {
	lines := common.ReadFileToLines("day4", "puzzle1input")
	scratchcards := newScratchcardsFromLines(lines)

	points := 0
	for _, scratchcard := range scratchcards {
		points += scratchcard.calculatePoints()
	}

	fmt.Println("Day4 Puzzle1")
	fmt.Println("points=", points)
}

type scratchcard struct {
	cardNo         int
	winningNumbers []int
	cardNumbers    []int
}

func (s *scratchcard) calculatePoints() int {
	count := 0
	for _, cardNumber := range s.cardNumbers {
		if slices.Contains(s.winningNumbers, cardNumber) {
			count += 1
		}
	}

	if count == 0 {
		return 0
	}

	point := 1
	for i := 0; i < count-1; i += 1 {
		point *= 2
	}
	return point
}

func newScratchcardsFromLines(lines []string) []scratchcard {
	scratchcards := make([]scratchcard, len(lines))
	for i, line := range lines {
		scratchcards[i] = newScratchcard(line)
	}
	return scratchcards
}

func newScratchcard(line string) scratchcard {
	elems := strings.Split(line, ":")
	cardNo, err := strconv.Atoi(strings.Trim(strings.Replace(elems[0], "Card", "", -1), " "))
	if err != nil {
		panic(err)
	}

	r := regexp.MustCompile("\\s+")
	elems = strings.Split(elems[1], "|")

	winningNumberStrings := r.Split(strings.Trim(elems[0], " "), -1)
	winningNumbers := make([]int, len(winningNumberStrings))
	for i, winningNumberString := range winningNumberStrings {
		winningNumber, err := strconv.Atoi(winningNumberString)
		if err != nil {
			panic(err)
		}
		winningNumbers[i] = winningNumber
	}

	cardNumberStrings := r.Split(strings.Trim(elems[1], " "), -1)
	cardNumbers := make([]int, len(cardNumberStrings))
	for i, cardNumberString := range cardNumberStrings {
		cardNumber, err := strconv.Atoi(cardNumberString)
		if err != nil {
			panic(err)
		}
		cardNumbers[i] = cardNumber
	}

	return scratchcard{cardNo: cardNo, winningNumbers: winningNumbers, cardNumbers: cardNumbers}
}
