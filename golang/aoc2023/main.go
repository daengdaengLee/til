package main

import (
	"aoc2023/day1"
	"aoc2023/day2"
	"aoc2023/day3"
	"aoc2023/day4"
	"aoc2023/day5"
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
	case "2":
		switch puzzleArg {
		case "1":
			day2.SolvePuzzle1()
		case "2":
			day2.SolvePuzzle2()
		default:
			day2.SolvePuzzle1()
			day2.SolvePuzzle2()
		}
	case "3":
		switch puzzleArg {
		case "1":
			day3.SolvePuzzle1()
		case "2":
			day3.SolvePuzzle2()
		default:
			day3.SolvePuzzle1()
			day3.SolvePuzzle2()
		}
	case "4":
		switch puzzleArg {
		case "1":
			day4.SolvePuzzle1()
		case "2":
			day4.SolvePuzzle2()
		default:
			day4.SolvePuzzle1()
			day4.SolvePuzzle2()
		}
	case "5":
		switch puzzleArg {
		case "1":
			day5.SolvePuzzle1()
		case "2":
			day5.SolvePuzzle2()
		default:
			day5.SolvePuzzle1()
			day5.SolvePuzzle2()
		}
	default:
		day1.SolvePuzzle1()
		day1.SolvePuzzle2()
		day2.SolvePuzzle1()
		day2.SolvePuzzle2()
		day3.SolvePuzzle1()
		day3.SolvePuzzle2()
		day4.SolvePuzzle1()
		day4.SolvePuzzle2()
		day5.SolvePuzzle1()
		day5.SolvePuzzle2()
	}
}
