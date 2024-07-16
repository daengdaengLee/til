package main

import (
	"fmt"
	"io"
	"mime/multipart"
	"net/http"
	"os"
	"path/filepath"
)

func main() {
	wd, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	http.HandleFunc("/file", func(writer http.ResponseWriter, request *http.Request) {
		fmt.Println(request.Header)
		err := request.ParseMultipartForm(1024)
		if err != nil {
			fmt.Println(err)
			return
		}
		fileName := request.MultipartForm.Value["name"][0]
		filePath := filepath.Join(wd, fileName)
		fileHeader := request.MultipartForm.File["file"][0]
		file, err := fileHeader.Open()
		if err != nil {
			fmt.Println(err)
			return
		}
		defer func(file multipart.File) {
			err := file.Close()
			if err != nil {
				fmt.Println(err)
			}
		}(file)
		outputFile, err := os.Create(filePath)
		if err != nil {
			fmt.Println(err)
			return
		}
		defer func(outputFile *os.File) {
			err := outputFile.Close()
			if err != nil {
				fmt.Println(err)
			}
		}(outputFile)
		_, err = io.Copy(outputFile, file)
		if err != nil {
			fmt.Println(err)
			return
		}

		writer.Header().Set("Content-Type", "application/json")
		writer.WriteHeader(http.StatusOK)
		_, err = writer.Write([]byte(`{"message": "OK"}`))
		if err != nil {
			fmt.Println(err)
			return
		}
	})
	err = http.ListenAndServe(":8080", nil)
	if err != nil {
		panic(err)
	}
}
