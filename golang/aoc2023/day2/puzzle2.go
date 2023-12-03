package day2

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
)

func SolvePuzzle2() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day2", "puzzle2input")

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
		power := 1
		for _, count := range []int{red, green, blue} {
			if count > 0 {
				power *= count
			}
		}
		sum += power
	}

	fmt.Println("Day2 Puzzle2")
	fmt.Println("sum=", sum)
}
