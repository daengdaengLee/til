package day5

type almanacSeed struct {
	start  int
	length int
}

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
