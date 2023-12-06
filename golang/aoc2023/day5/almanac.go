package day5

type almanacMap struct {
	source      string
	destination string
	mappings    []almanacMapping
}

type almanacMapping struct {
	sourceStart      int
	destinationStart int
	rangeLength      int
}
