package day9

import (
	"strconv"
	"strings"
)

func mustParseLines(lines []string) [][]int {
	numsLines, err := parseLines(lines)
	if err != nil {
		panic(err)
	}
	return numsLines
}

func parseLines(lines []string) ([][]int, error) {
	numsLines := make([][]int, len(lines))
	for i, line := range lines {
		nums, err := parseLine(line)
		if err != nil {
			return nil, err
		}
		numsLines[i] = nums
	}
	return numsLines, nil
}

func parseLine(line string) ([]int, error) {
	tokens := strings.Split(line, " ")
	nums := make([]int, len(tokens))
	for i, token := range tokens {
		num, err := strconv.Atoi(token)
		if err != nil {
			return nil, err
		}
		nums[i] = num
	}
	return nums, nil
}

func makeHistoryStack(history []int) [][]int {
	stack := make([][]int, 0, len(history))
	stack = append(stack, history)
	top := history
	for !isSeqAllZero(top) {
		nextSeq := makeNextSeq(top)
		stack = append(stack, nextSeq)
		top = nextSeq
	}
	return stack
}

func isSeqAllZero(seq []int) bool {
	for _, v := range seq {
		if v != 0 {
			return false
		}
	}
	return true
}

func makeNextSeq(seq []int) []int {
	if len(seq) == 0 {
		return []int{}
	}

	nextSeqLen := len(seq) - 1
	if nextSeqLen < 0 {
		nextSeqLen = 0
	}
	nextSeq := make([]int, 0, nextSeqLen)
	for i := 1; i < len(seq); i += 1 {
		a := seq[i-1]
		b := seq[i]
		nextSeq = append(nextSeq, b-a)
	}
	return nextSeq
}
