package day2

import (
	"strconv"
	"strings"
)

func getCubeCount(game string) (int, int, int) {
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
