package day2

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

const RED int = 12
const GREEN int = 13
const BLUE int = 14

func SolvePuzzle1() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day2", "puzzle1input")

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

	sum := 0

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		game := scanner.Text()
		maxRed, maxGreen, maxBlue := getMaxCubeCount(game)
		if maxRed <= RED && maxGreen <= GREEN && maxBlue <= BLUE {
			sum += getGameId(game)
		}
	}

	fmt.Println("Day2 Puzzle1")
	fmt.Println("sum=", sum)
}

func getMaxCubeCount(game string) (int, int, int) {
	maxRed := 0
	maxGreen := 0
	maxBlue := 0

	idx := strings.Index(game, ":")
	if idx == -1 {
		panic("invalid game input: " + game)
	}
	records := game[idx+2:]

	for _, record := range strings.Split(records, ";") {
		record = strings.Trim(record, " ")
		for _, cube := range strings.Split(record, ",") {
			cube = strings.Trim(cube, " ")
			split := strings.Split(cube, " ")
			count, err := strconv.Atoi(split[0])
			if err != nil {
				panic("invalid game input: " + game)
			}
			color := split[1]

			switch color {
			case "red":
				if count > maxRed {
					maxRed = count
				}
			case "green":
				if count > maxGreen {
					maxGreen = count
				}
			case "blue":
				if count > maxBlue {
					maxBlue = count
				}
			}
		}
	}

	return maxRed, maxGreen, maxBlue
}

func getGameId(game string) int {
	idx := strings.Index(game, ":")
	if idx == -1 {
		panic("invalid game input: " + game)
	}
	id, err := strconv.Atoi(game[5:idx])
	if err != nil {
		panic("invalid game input: " + game)
	}
	return id
}
