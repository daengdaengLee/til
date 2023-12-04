package common

import (
	"bufio"
	"os"
	"path/filepath"
)

func ReadFileToLines(pathElems ...string) []string {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	path := filepath.Join(pathElems...)
	path = filepath.Join(dir, path)

	file, err := os.Open(path)
	if err != nil {
		panic(err)
	}
	defer func(file *os.File) {
		err := file.Close()
		if err != nil {
			panic(err)
		}
	}(file)

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		lines = append(lines, line)
	}
	return lines
}
