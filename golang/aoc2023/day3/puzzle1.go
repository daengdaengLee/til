package day3

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
)

func SolvePuzzle1() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day3", "puzzle1input")

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

	sum := 0
	for i, line := range engine {
		for _, loc := range findAllIndex(line) {
			for _, surround := range findSurrounds(engine, i, loc) {
				if isSymbol(surround) {
					num, err := strconv.Atoi(strings.Join(line[loc[0]:loc[1]+1], ""))
					if err != nil {
						panic(err)
					}
					sum += num
					break
				}
			}
		}
	}
	fmt.Println("Day3 Puzzle1")
	fmt.Println("sum=", sum)
}

func findSurrounds(engine [][]string, i int, js []int) []string {
	var surrounded []string

	line := engine[i]

	startJ := js[0] - 1
	if startJ < 0 {
		startJ = 0
	}
	if startJ != js[0] {
		surrounded = append(surrounded, line[startJ])
	}

	endJ := js[1] + 1
	if endJ >= len(line) {
		endJ = len(line) - 1
	}
	if endJ != js[1] {
		surrounded = append(surrounded, line[endJ])
	}

	if i > 0 {
		line := engine[i-1]
		for j := startJ; j <= endJ; j += 1 {
			surrounded = append(surrounded, line[j])
		}
	}

	if i < len(line)-1 {
		line := engine[i+1]
		for j := startJ; j <= endJ; j += 1 {
			surrounded = append(surrounded, line[j])
		}
	}

	return surrounded
}

func findAllIndex(line []string) [][]int {
	var locs [][]int

	i := 0
	for i < len(line) {
		loc := findIndex(line, i)
		if loc == nil {
			break
		}
		locs = append(locs, loc)
		i = loc[1] + 1
	}

	return locs
}

func findIndex(line []string, startIdx int) []int {
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

func isSymbol(s string) bool {
	if isDigit(s) {
		return false
	}
	return s != "."
}
