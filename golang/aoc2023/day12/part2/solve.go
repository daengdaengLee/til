package part2

import (
	"aoc2023/common"
	"fmt"
	"slices"
	"strconv"
	"strings"
)

func Solve() {
	lines := common.ReadFileToLines("day12", "input")
	sum := 0
	cache := make(map[string]int)
	for _, line := range lines {
		dots, nums := parse(line)

		unfoldDots := make([]string, 0, len(dots)*5+5)
		unfoldNums := make([]int, 0, len(nums)*5)
		for i := 0; i < 5; i += 1 {
			unfoldDots = append(unfoldDots, dots...)
			unfoldDots = append(unfoldDots, "?")

			unfoldNums = append(unfoldNums, nums...)
		}
		unfoldDots = unfoldDots[0 : len(unfoldDots)-1]

		sum += count(cache, unfoldDots, unfoldNums)
	}
	fmt.Println("Day12 Part2")
	fmt.Println("sum=", sum)
}

func parse(line string) ([]string, []int) {
	splitLine := strings.Split(line, " ")

	dots := strings.Split(splitLine[0], "")

	numTokens := strings.Split(splitLine[1], ",")
	nums := make([]int, len(numTokens))
	for i, numToken := range numTokens {
		num, err := strconv.Atoi(numToken)
		if err != nil {
			panic(err)
		}
		nums[i] = num
	}

	return dots, nums
}

func count(cache map[string]int, dots []string, nums []int) int {
	key := serialize(dots, nums)
	cachedValue, isCached := cache[key]
	if isCached {
		return cachedValue
	}

	if len(nums) == 0 {
		if slices.Contains(dots, "#") {
			cache[key] = 0
			return 0
		} else {
			cache[key] = 1
			return 1
		}
	}

	if len(dots) == 0 {
		cache[key] = 0
		return 0
	}

	dot := dots[0]
	num := nums[0]

	doOperational := func() int {
		return count(cache, dots[1:], nums)
	}

	doBroken := func() int {
		if len(dots) < num {
			return 0
		}

		for i := 0; i < num; i += 1 {
			if dots[i] == "." {
				return 0
			}
		}

		if len(dots) == num {
			if len(nums) == 1 {
				return 1
			}
			return 0
		}

		if (dots[num] == ".") || (dots[num] == "?") {
			return count(cache, dots[num+1:], nums[1:])
		}

		return 0
	}

	result := 0
	switch dot {
	case ".":
		result = doOperational()
	case "#":
		result = doBroken()
	default:
		result = doOperational() + doBroken()
	}
	cache[key] = result

	return result
}

func serialize(dots []string, nums []int) string {
	tokens := make([]string, 0, len(dots)+1+len(nums)*2)
	tokens = append(tokens, dots...)
	tokens = append(tokens, " ")
	for _, num := range nums {
		tokens = append(tokens, strconv.Itoa(num), ",")
	}
	return strings.Join(tokens[0:len(tokens)-1], "")
}
