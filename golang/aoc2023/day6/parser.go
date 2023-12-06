package day6

import (
	"regexp"
	"strconv"
	"strings"
)

func parseRecords1(lines []string) []record {
	var times []int
	var distances []int
	for _, line := range lines {
		if isTimes(line) {
			times = parseTokens(line)
		} else if isDistances(line) {
			distances = parseTokens(line)
		}
	}

	records := make([]record, len(times))
	for i, time := range times {
		records[i] = record{time: time, distance: distances[i]}
	}
	return records
}

func parseRecords2(lines []string) record {
	var timeLine string
	var distanceLine string
	for _, line := range lines {
		if isTimes(line) {
			timeLine = line
		} else if isDistances(line) {
			distanceLine = line
		}
	}

	time := mustAtoi(strings.Join(separateTokens(removeHeader(timeLine)), ""))
	distance := mustAtoi(strings.Join(separateTokens(removeHeader(distanceLine)), ""))

	return record{time: time, distance: distance}
}

func isTimes(line string) bool {
	return strings.Contains(line, "Time:")
}

func isDistances(line string) bool {
	return strings.Contains(line, "Distance:")
}

func parseTokens(line string) []int {
	line = removeHeader(line)
	tokens := separateTokens(line)
	parsedTokens := make([]int, len(tokens))
	for i, token := range tokens {
		parsedTokens[i] = mustAtoi(token)
	}
	return parsedTokens
}

func removeHeader(line string) string {
	idx := strings.Index(line, ":")
	if idx > -1 {
		line = line[idx+1:]
	}
	return strings.Trim(line, " ")
}

func separateTokens(line string) []string {
	sep := regexp.MustCompile("\\s+")
	return sep.Split(line, -1)
}

func mustAtoi(s string) int {
	i, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}
	return i
}
