package day6

func calculateWayCount(record record) int {
	count := 0
	for a := 1; a <= record.time; a += 1 {
		b := record.time - a
		d := a * b
		if d > record.distance {
			count += 1
		}
	}
	return count
}
