package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/labstack/echo/v4"
	"io"
	"net/http"
	"ssetest/common"
)

func main() {
	e := echo.New()
	e.POST("/", func(c echo.Context) error {
		reqBody := make(map[string]string)
		if err := c.Bind(&reqBody); err != nil {
			return c.String(http.StatusBadRequest, err.Error())
		}
		value := reqBody["value"]
		value = fmt.Sprintf("proxy->%s", value)
		reqBody["value"] = value
		reqBodyJson, err := json.Marshal(reqBody)
		if err != nil {
			return c.String(http.StatusBadRequest, err.Error())
		}

		proxyReq, err := http.NewRequest(http.MethodPost, common.ServerUrl, bytes.NewBuffer(reqBodyJson))
		if err != nil {
			return c.String(http.StatusBadRequest, err.Error())
		}
		proxyReq.Header.Set("Content-Type", "application/json")
		proxyClient := &http.Client{}
		proxyRes, err := proxyClient.Do(proxyReq)
		if err != nil {
			return c.String(http.StatusBadRequest, err.Error())
		}
		defer func(Body io.ReadCloser) {
			err2 := Body.Close()
			if err2 != nil {
				fmt.Printf("Proxy 응답 닫기 실패\ncaused by: %v\n", err2)
			}
		}(proxyRes.Body)

		proxyResChannel := make(chan []byte)
		go func() {
			buf := make([]byte, 1024)
			for {
				n, err := proxyRes.Body.Read(buf)
				if n > 0 {
					proxyResChannel <- buf
				}
				if err == io.EOF {
					close(proxyResChannel)
					return
				}
				if err != nil {
					panic(err)
				}
			}
		}()

		req := c.Request()
		res := c.Response()
		res.Header().Set("Content-Type", "text/event-stream")
		res.Header().Set("Cache-Control", "no-cache")
		res.Header().Set("Connection", "keep-alive")

		fmt.Println(">>>>> 시작 >>>>>")
		defer fmt.Println("<<<<< 끝 <<<<<")
		for {
			select {
			case <-req.Context().Done():
				return nil
			case data, ok := <-proxyResChannel:
				fmt.Println(string(data))
				if _, err := fmt.Fprintf(res, string(data)); err != nil {
					return err
				}
				res.Flush()

				if !ok {
					return nil
				}
			}
		}
	})
	err := e.Start(fmt.Sprintf(":%d", common.ProxyPort))
	if err != nil {
		panic(err)
	}
}
