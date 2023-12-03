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
		red, green, blue := getCubeCount(game)
		if red <= RED && green <= GREEN && blue <= BLUE {
			sum += getGameId(game)
		}
	}

	fmt.Println("Day2 Puzzle1")
	fmt.Println("sum=", sum)
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
