package part1

import (
	"aoc2023/common"
	"errors"
	"fmt"
	"slices"
	"strconv"
	"strings"
)

func Solve() {
	lines := common.ReadFileToLines("day12", "input")

	sum := 0
	for _, line := range lines {
		conditionRecord := NewConditionRecord(line)
		arrangement := conditionRecord.Arrange()
		sum += len(arrangement)
	}

	fmt.Println("Day12 Part1")
	fmt.Println("sum=", sum)
}

type Status string

const (
	OperationalStatus = Status(".")
	DamagedStatus     = Status("#")
	UnknownStatus     = Status("?")
)

func (status *Status) IsOperational() bool {
	return *status == OperationalStatus
}

func (status *Status) IsDamaged() bool {
	return *status == DamagedStatus
}

func (status *Status) IsUnknown() bool {
	return *status == UnknownStatus
}

type Statuses []Status

func EmptyStatuses() Statuses {
	return make(Statuses, 0)
}

func NewStatuses(line string) Statuses {
	tokens := strings.Split(line, "")
	statuses := make(Statuses, len(tokens))
	for i, token := range tokens {
		statuses[i] = Status(token)
	}
	return statuses
}

func (statuses *Statuses) Clone() Statuses {
	return slices.Clone(*statuses)
}

func (statuses *Statuses) Append(status Status) {
	*statuses = append(*statuses, status)
}

func (statuses *Statuses) DamagedGroups() DamagedGroups {
	damagedGroups := make(DamagedGroups, 0, len(*statuses))
	damagedGroup := 0
	for _, status := range *statuses {
		if status.IsOperational() {
			if damagedGroup > 0 {
				damagedGroups = append(damagedGroups, damagedGroup)
				damagedGroup = 0
			}
		} else if status.IsDamaged() {
			damagedGroup += 1
		} else {
			panic(errors.New(fmt.Sprintf("invalid statuses: %+v", *statuses)))
		}
	}
	if damagedGroup > 0 {
		damagedGroups = append(damagedGroups, damagedGroup)
		damagedGroup = 0
	}
	return damagedGroups
}

type DamagedGroups []int

func NewDamagedGroups(line string) DamagedGroups {
	tokens := strings.Split(line, ",")
	damagedGroups := make(DamagedGroups, len(tokens))
	for i, token := range tokens {
		damagedGroup, err := strconv.Atoi(token)
		if err != nil {
			panic(err)
		}
		damagedGroups[i] = damagedGroup
	}
	return damagedGroups
}

func (damagedGroups *DamagedGroups) Clone() DamagedGroups {
	return slices.Clone(*damagedGroups)
}

func (damagedGroups *DamagedGroups) IsCompatible(another DamagedGroups) bool {
	if len(another) == 0 {
		return true
	}

	if len(another) > len(*damagedGroups) {
		return false
	}

	lastIdx := len(another) - 1
	for i, damagedGroup := range another[0:lastIdx] {
		if damagedGroup != (*damagedGroups)[i] {
			return false
		}
	}
	return another[lastIdx] <= (*damagedGroups)[lastIdx]
}

func (damagedGroups *DamagedGroups) IsMatched(another DamagedGroups) bool {
	if len(*damagedGroups) != len(another) {
		return false
	}
	for i, damagedGroup := range *damagedGroups {
		if damagedGroup != another[i] {
			return false
		}
	}
	return true
}

type ConditionRecord struct {
	statuses      Statuses
	damagedGroups DamagedGroups
}

func NewConditionRecord(line string) ConditionRecord {
	tokens := strings.Split(line, " ")
	statuses := NewStatuses(tokens[0])
	damagedGroups := NewDamagedGroups(tokens[1])
	return ConditionRecord{statuses: statuses, damagedGroups: damagedGroups}
}

func (conditionRecord *ConditionRecord) Clone() ConditionRecord {
	cloneStatuses := conditionRecord.statuses.Clone()
	cloneDamagedGroups := conditionRecord.damagedGroups.Clone()
	return ConditionRecord{
		statuses:      cloneStatuses,
		damagedGroups: cloneDamagedGroups,
	}
}

func (conditionRecord *ConditionRecord) Arrange() [][]Status {
	var arrange [][]Status

	candidates := []ConditionRecord{{
		statuses:      EmptyStatuses(),
		damagedGroups: conditionRecord.damagedGroups.Clone(),
	}}

	for _, status := range conditionRecord.statuses {
		if len(candidates) == 0 {
			break
		}

		newCandidates := make([]ConditionRecord, 0, len(candidates)*2)
		for _, candidate := range candidates {
			if status.IsUnknown() {
				operationalCandidate := candidate.Clone()
				operationalCandidate.Append(OperationalStatus)
				if conditionRecord.IsCompatibleDamagedGroups(operationalCandidate.damagedGroups) {
					newCandidates = append(newCandidates, operationalCandidate)
				}

				damagedCandidate := candidate.Clone()
				damagedCandidate.Append(DamagedStatus)
				if conditionRecord.IsCompatibleDamagedGroups(damagedCandidate.damagedGroups) {
					newCandidates = append(newCandidates, damagedCandidate)
				}
			} else {
				newCandidate := candidate.Clone()
				newCandidate.Append(status)
				if conditionRecord.IsCompatibleDamagedGroups(newCandidate.damagedGroups) {
					newCandidates = append(newCandidates, newCandidate)
				}
			}
		}
		candidates = newCandidates
	}

	for _, candidate := range candidates {
		if conditionRecord.IsMatchedDamagedGroups(candidate.damagedGroups) {
			arrange = append(arrange, candidate.statuses)
		}
	}

	return arrange
}

func (conditionRecord *ConditionRecord) Append(status Status) {
	conditionRecord.statuses.Append(status)
	conditionRecord.damagedGroups = conditionRecord.statuses.DamagedGroups()
}

func (conditionRecord *ConditionRecord) IsCompatibleDamagedGroups(damagedGroups DamagedGroups) bool {
	return conditionRecord.damagedGroups.IsCompatible(damagedGroups)
}

func (conditionRecord *ConditionRecord) IsMatchedDamagedGroups(damagedGroups DamagedGroups) bool {
	return conditionRecord.damagedGroups.IsMatched(damagedGroups)
}
