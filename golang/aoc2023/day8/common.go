package day8

import "strings"

func parseInstructions(line string) []string {
	return strings.Split(line, "")
}

func parseNodeMap(lines []string) map[string][]string {
	nodeMap := make(map[string][]string, len(lines)-2)
	for _, line := range lines {
		tokens := strings.Split(line, "=")

		node := strings.Trim(tokens[0], " ")

		leftAndRightNodeTokens := strings.Split(strings.Trim(tokens[1], " "), ",")
		leftNode := strings.Trim(strings.Replace(leftAndRightNodeTokens[0], "(", "", -1), " ")
		rightNode := strings.Trim(strings.Replace(leftAndRightNodeTokens[1], ")", "", -1), " ")
		nodeMap[node] = []string{leftNode, rightNode}
	}
	return nodeMap
}

func mustGetNextNode(node string, instruction string, nodeMap map[string][]string) string {
	nextNodes, isNextNodes := nodeMap[node]
	if !isNextNodes {
		panic("no next nodes from " + node)
	}
	if len(nextNodes) != 2 {
		panic("invalid next nodes from " + node)
	}
	if instruction == "L" {
		return nextNodes[0]
	} else if instruction == "R" {
		return nextNodes[1]
	}
	panic("invalid instruction" + instruction)
}

type infiniteInstructions struct {
	idx          int
	instructions []string
}

func (ii *infiniteInstructions) nextInstruction() string {
	instruction := ii.instructions[ii.idx]
	ii.idx += 1
	ii.idx %= len(ii.instructions)
	return instruction
}

func newInfiniteInstructions(instructions []string) infiniteInstructions {
	return infiniteInstructions{
		idx:          0,
		instructions: instructions,
	}
}
