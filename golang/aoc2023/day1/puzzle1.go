package day1

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

func SolvePuzzle1() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day1", "puzzle1input")

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
		line := strings.Split(scanner.Text(), "")
		l := len(line)

		first := 0
		for i := 0; i < l; i += 1 {
			c := line[i]
			n, err := strconv.Atoi(c)
			if err == nil {
				first = n
				break
			}
		}

		last := 0
		for i := l - 1; i >= 0; i -= 1 {
			c := line[i]
			n, err := strconv.Atoi(c)
			if err == nil {
				last = n
				break
			}
		}

		n := first*10 + last
		sum += n
	}

	fmt.Println("Day1 Puzzle1")
	fmt.Println("sum=", sum)
}
