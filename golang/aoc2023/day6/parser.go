package day6

import (
	"regexp"
	"strconv"
	"strings"
)

func parseRecords(lines []string) []record {
	var times []int
	var distances []int
	for _, line := range lines {
		if isTimes(line) {
			times = parseTimes(line)
		} else if isDistances(line) {
			distances = parseDistances(line)
		}
	}

	records := make([]record, len(times))
	for i, time := range times {
		records[i] = record{time: time, distance: distances[i]}
	}
	return records
}

func isTimes(line string) bool {
	return strings.Contains(line, "Time:")
}

func parseTimes(line string) []int {
	line = strings.Trim(strings.Replace(line, "Time:", "", -1), " ")
	sep := regexp.MustCompile("\\s+")
	tokens := sep.Split(line, -1)
	times := make([]int, len(tokens))
	for i, token := range tokens {
		time, err := strconv.Atoi(strings.Trim(token, " "))
		if err != nil {
			panic(err)
		}
		times[i] = time
	}
	return times
}

func isDistances(line string) bool {
	return strings.Contains(line, "Distance:")
}

func parseDistances(line string) []int {
	line = strings.Trim(strings.Replace(line, "Distance:", "", -1), " ")
	sep := regexp.MustCompile("\\s+")
	tokens := sep.Split(line, -1)
	distances := make([]int, len(tokens))
	for i, token := range tokens {
		distance, err := strconv.Atoi(strings.Trim(token, " "))
		if err != nil {
			panic(err)
		}
		distances[i] = distance
	}
	return distances
}
