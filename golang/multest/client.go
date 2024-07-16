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

	pr, pw := io.Pipe()
	defer func(pr *io.PipeReader) {
		err2 := pr.Close()
		if err2 != nil {
			fmt.Println("close pr", err2)
		}
	}(pr)
	defer func(pw *io.PipeWriter) {
		err2 := pw.Close()
		if err2 != nil {
			fmt.Println("close pw", err2)
		}
	}(pw)
	writer := multipart.NewWriter(pw)
	defer func(writer *multipart.Writer) {
		err2 := writer.Close()
		if err2 != nil {
			fmt.Println("close writer", err2)
		}
	}(writer)
	ct := writer.FormDataContentType()

	req, err := http.NewRequest(http.MethodPost, "http://localhost:8080/file", pr)
	if err != nil {
		panic(err)
	}
	req.Header.Set("Content-Type", ct)

	client := &http.Client{}
	go func() {
		defer func() {
			if r := recover(); r != nil {
				_ = pw.CloseWithError(fmt.Errorf("panic: %v", r))
			}
		}()
		defer func() {
			_ = pw.CloseWithError(writer.Close())
		}()

		err2 := writer.WriteField("name", "output.txt")
		if err2 != nil {
			_ = pw.CloseWithError(err2)
			return
		}

		inputFile, err2 := os.Open(filepath.Join(wd, "input.txt"))
		if err2 != nil {
			_ = pw.CloseWithError(err2)
			return
		}
		defer func(inputFile *os.File) {
			err3 := inputFile.Close()
			if err3 != nil {
				fmt.Println("close inputFile", err3)
			}
		}(inputFile)

		part, err2 := writer.CreateFormFile("file", "input.txt")
		if err2 != nil {
			_ = pw.CloseWithError(err2)
			return
		}
		_, err2 = io.Copy(part, inputFile)
		if err2 != nil {
			_ = pw.CloseWithError(err2)
			return
		}
	}()
	res, err := client.Do(req)
	if err != nil {
		panic(err)
	}
	defer func(Body io.ReadCloser) {
		err2 := Body.Close()
		if err2 != nil {
			fmt.Println("close res.Body", err)
		}
	}(res.Body)
	fmt.Println(res.StatusCode)
	fmt.Println(res.Header)
	resBody, err := io.ReadAll(res.Body)
	if err != nil {
		panic(err)
	}
	fmt.Println(string(resBody))
}
