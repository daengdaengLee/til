package day1

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
)

func SolvePuzzle2() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(dir, "day1", "puzzle2input")

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

	r := regexp.MustCompile("[1-9]|one|two|three|four|five|six|seven|eight|nine")
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := strings.Split(scanner.Text(), "")
		var values []string
		for i := 0; i < len(line); i += 1 {
			subLine := strings.Join(line[i:], "")
			value := r.FindString(subLine)
			if len(value) > 0 {
				values = append(values, value)
			}
		}
		first := parseToInt(values[0])
		last := parseToInt(values[len(values)-1])
		num := first*10 + last
		sum += num

		fmt.Println(line)
		fmt.Println(values)
		fmt.Println(num)
	}

	fmt.Println("sum=", sum)
}

func parseToInt(val string) int {
	switch val {
	case "one":
		return 1
	case "two":
		return 2
	case "three":
		return 3
	case "four":
		return 4
	case "five":
		return 5
	case "six":
		return 6
	case "seven":
		return 7
	case "eight":
		return 8
	case "nine":
		return 9
	default:
		num, err := strconv.Atoi(val)
		if err != nil {
			panic(err)
		}
		return num
	}
}
