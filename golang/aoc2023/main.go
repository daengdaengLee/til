package main

import (
	"aoc2023/day1"
	"os"
	"regexp"
)

func main() {
	dayR := regexp.MustCompile("^-day=")
	puzzleR := regexp.MustCompile("^-puzzle=")
	var dayArg string
	var puzzleArg string
	for _, arg := range os.Args {
		dayArgResult := dayR.FindStringIndex(arg)
		if dayArgResult != nil {
			dayArg = arg[dayArgResult[1]:]
			continue
		}

		puzzleArgResult := puzzleR.FindStringIndex(arg)
		if puzzleArgResult != nil {
			puzzleArg = arg[puzzleArgResult[1]:]
			continue
		}
	}

	switch dayArg {
	case "1":
		switch puzzleArg {
		case "1":
			day1.SolvePuzzle1()
		case "2":
			day1.SolvePuzzle2()
		default:
			day1.SolvePuzzle1()
			day1.SolvePuzzle2()
		}
	default:
		day1.SolvePuzzle1()
		day1.SolvePuzzle2()
	}
}
