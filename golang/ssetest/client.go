package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"ssetest/common"
)

func main() {
	fmt.Println("call server >>>")
	callServer()
	fmt.Println("<<< call server")

	fmt.Println("call proxy >>>")
	callProxy()
	fmt.Println("<<< call proxy")
}

func callServer() {
	reqBody := map[string]string{"value": "call server"}
	reqBodyJson, err := json.Marshal(reqBody)
	if err != nil {
		panic(err)
	}
	req, err := http.NewRequest("POST", common.ServerUrl, bytes.NewBuffer(reqBodyJson))
	if err != nil {
		panic(err)
	}
	req.Header.Set("Content-Type", "application/json")
	err = doReq(req)
	if err != nil {
		panic(err)
	}
}

func callProxy() {
	reqBody := map[string]string{"value": "call proxy"}
	reqBodyJson, err := json.Marshal(reqBody)
	if err != nil {
		panic(err)
	}
	req, err := http.NewRequest("POST", common.ProxyUrl, bytes.NewBuffer(reqBodyJson))
	if err != nil {
		panic(err)
	}
	req.Header.Set("Content-Type", "application/json")
	err = doReq(req)
	if err != nil {
		panic(err)
	}
}

func doReq(req *http.Request) (err error) {
	client := &http.Client{}
	res, err := client.Do(req)
	if err != nil {
		return

	}
	defer func(Body io.ReadCloser) {
		err2 := Body.Close()
		if err2 != nil {
			fmt.Printf("API 응답 Body 닫기 실패\ncaused by: %v", err2)
		}
	}(res.Body)
	buf := make([]byte, 1024)
	for {
		var n int
		n, err = res.Body.Read(buf)
		if n > 0 {
			fmt.Print(string(buf))
		}
		if err == io.EOF {
			fmt.Println("끝")
			err = nil
			return
		}
		if err != nil {
			return
		}
	}
}
