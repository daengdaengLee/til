package day3

import (
	"bufio"
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
)

func getEngine() [][]string {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day3", "input")

	file, err := os.Open(path)
	if err != nil {
		panic(err)
	}
	defer func(file *os.File) {
		err := file.Close()
		if err != nil {
			panic(err)
		}
	}(file)

	var engine [][]string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := strings.Split(scanner.Text(), "")
		engine = append(engine, line)
	}
	return engine
}

func getAllLocs(engine [][]string) [][][]int {
	var allLocs [][][]int
	for _, line := range engine {
		allLocs = append(allLocs, findLocs(line))
	}
	return allLocs
}

func findLocs(line []string) [][]int {
	var locs [][]int

	i := 0
	for i < len(line) {
		loc := findLoc(line, i)
		if loc == nil {
			break
		}
		locs = append(locs, loc)
		i = loc[1] + 1
	}

	return locs
}

func findLoc(line []string, startIdx int) []int {
	idx1 := findDigitIndex(line, startIdx)
	if idx1 == -1 {
		return nil
	}

	idx2 := findNonDigitIndex(line, idx1+1)
	if idx2 == -1 {
		return []int{idx1, len(line) - 1}
	}

	return []int{idx1, idx2 - 1}
}

func findDigitIndex(line []string, startIdx int) int {
	for i := startIdx; i < len(line); i += 1 {
		if isDigit(line[i]) {
			return i
		}
	}
	return -1
}

func findNonDigitIndex(line []string, startIdx int) int {
	for i := startIdx; i < len(line); i += 1 {
		if !isDigit(line[i]) {
			return i
		}
	}
	return -1
}

func isDigit(s string) bool {
	matched, err := regexp.MatchString("^[0-9]$", s)
	if err != nil {
		panic(err)
	}
	return matched
}

func parseNum(line []string, loc []int) int {
	num, err := strconv.Atoi(strings.Join(line[loc[0]:loc[1]+1], ""))
	if err != nil {
		panic(err)
	}
	return num
}
