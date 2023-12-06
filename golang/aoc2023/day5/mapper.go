package day5

func mapSeedToLocation(almanacMaps map[string]almanacMap, seed int) int {
	source := "seed"
	value := seed
	for source != "location" {
		almanacMap := almanacMaps[source]
		source = almanacMap.destination
		value = mapByAlmanacMap(almanacMap, value)
	}
	return value
}

func mapByAlmanacMap(almanacMap almanacMap, value int) int {
	for _, almanacMapping := range almanacMap.mappings {
		if !isMatchedMapping(almanacMapping, value) {
			continue
		}
		return mapByAlmanacMapping(almanacMapping, value)
	}
	return value
}

func isMatchedMapping(almanacMapping almanacMapping, value int) bool {
	return almanacMapping.sourceStart <= value &&
		(almanacMapping.sourceStart+almanacMapping.rangeLength) >= value
}

func mapByAlmanacMapping(almanacMapping almanacMapping, value int) int {
	diff := value - almanacMapping.sourceStart
	return almanacMapping.destinationStart + diff
}
