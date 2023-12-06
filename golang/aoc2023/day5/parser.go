package day5

import (
	"strconv"
	"strings"
)

func parseSeeds1(lines []string) []int {
	strSeeds := strings.Split(strings.Replace(lines[0], "seeds: ", "", 1), " ")
	intSeeds := make([]int, len(strSeeds))
	for i, strSeed := range strSeeds {
		intSeed, err := strconv.Atoi(strSeed)
		if err != nil {
			panic(err)
		}
		intSeeds[i] = intSeed
	}
	return intSeeds
}

func parseSeeds2(lines []string) []almanacSeed {
	tokens := strings.Split(strings.Replace(lines[0], "seeds: ", "", 1), " ")
	almanacSeeds := make([]almanacSeed, 0, len(tokens)/2)
	for i := 0; i < len(tokens); i += 2 {
		start, err := strconv.Atoi(tokens[i])
		if err != nil {
			panic(err)
		}
		length, err := strconv.Atoi(tokens[i+1])
		if err != nil {
			panic(err)
		}
		almanacSeeds = append(almanacSeeds, almanacSeed{start: start, length: length})
	}
	return almanacSeeds
}

func parseAlmanacMaps(lines []string) map[string]almanacMap {
	almanacMaps := make(map[string]almanacMap)

	for i := 0; i < len(lines); {
		line := lines[i]
		if !isAlmanacMapHeader(line) {
			i += 1
			continue
		}

		source, destination := parseAlmanacMapHeader(line)
		startIdx := i + 1
		endIdx := findEndIdx(lines, startIdx)
		l := endIdx - startIdx + 1
		mapping := make([]almanacMapping, l)
		for j := 0; j < l; j += 1 {
			mapping[j] = parseAlmanacMapping(lines[startIdx+j])
		}
		almanacMaps[source] = almanacMap{source: source, destination: destination, mappings: mapping}

		i = endIdx + 1
	}

	return almanacMaps
}

func isAlmanacMapHeader(line string) bool {
	return strings.Contains(line, "map:")
}

func parseAlmanacMapHeader(line string) (string, string) {
	tokens := strings.Split(strings.Replace(line, " map:", "", 1), "-to-")
	return tokens[0], tokens[1]
}

func findEndIdx(lines []string, startIdx int) int {
	for i := startIdx + 1; i < len(lines); i += 1 {
		if strings.Trim(lines[i], " ") != "" {
			continue
		}
		return i - 1
	}
	return len(lines) - 1
}

func parseAlmanacMapping(line string) almanacMapping {
	tokens := make([]int, 3)
	for i, s := range strings.Split(line, " ") {
		token, err := strconv.Atoi(s)
		if err != nil {
			panic(err)
		}
		tokens[i] = token
	}
	return almanacMapping{sourceStart: tokens[1], destinationStart: tokens[0], rangeLength: tokens[2]}
}
