package main

import (
	"aoc2023/day1"
	"aoc2023/day10"
	day11part1 "aoc2023/day11/part1"
	day11part2 "aoc2023/day11/part2"
	"aoc2023/day2"
	"aoc2023/day3"
	"aoc2023/day4"
	"aoc2023/day5"
	"aoc2023/day6"
	"aoc2023/day7"
	"aoc2023/day8"
	"aoc2023/day9"
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
	case "6":
		switch puzzleArg {
		case "1":
			day6.SolvePuzzle1()
		case "2":
			day6.SolvePuzzle2()
		default:
			day6.SolvePuzzle1()
			day6.SolvePuzzle2()
		}
	case "7":
		switch puzzleArg {
		case "1":
			day7.SolvePuzzle1()
		case "2":
			day7.SolvePuzzle2()
		default:
			day7.SolvePuzzle1()
			day7.SolvePuzzle2()
		}
	case "8":
		switch puzzleArg {
		case "1":
			day8.SolvePuzzle1()
		case "2":
			day8.SolvePuzzle2()
		default:
			day8.SolvePuzzle1()
			day8.SolvePuzzle2()
		}
	case "9":
		switch puzzleArg {
		case "1":
			day9.SolvePuzzle1()
		case "2":
			day9.SolvePuzzle2()
		default:
			day9.SolvePuzzle1()
			day9.SolvePuzzle2()
		}
	case "10":
		switch puzzleArg {
		case "1":
			day10.SolvePuzzle1()
		case "2":
			day10.SolvePuzzle2()
		default:
			day10.SolvePuzzle1()
			day10.SolvePuzzle2()
		}
	case "11":
		switch puzzleArg {
		case "1":
			day11part1.Solve()
		case "2":
			day11part2.Solve()
		default:
			day11part1.Solve()
			day11part2.Solve()
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
		day6.SolvePuzzle1()
		day6.SolvePuzzle2()
		day7.SolvePuzzle1()
		day7.SolvePuzzle2()
		day8.SolvePuzzle1()
		day8.SolvePuzzle2()
		day9.SolvePuzzle1()
		day9.SolvePuzzle2()
		day10.SolvePuzzle1()
		day10.SolvePuzzle2()
		day11part1.Solve()
		day11part2.Solve()
	}
}
