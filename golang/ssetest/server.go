package main

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"net/http"
	"ssetest/common"
	"time"
)

func main() {
	e := echo.New()
	e.POST("/", func(c echo.Context) error {
		reqBody := make(map[string]string)
		if err := c.Bind(&reqBody); err != nil {
			return c.String(http.StatusBadRequest, err.Error())
		}
		value := reqBody["value"]

		req := c.Request()
		res := c.Response()
		res.Header().Set("Content-Type", "text/event-stream")
		res.Header().Set("Cache-Control", "no-cache")
		res.Header().Set("Connection", "keep-alive")

		ticker := time.NewTicker(1 * time.Second)
		defer ticker.Stop()
		timer := time.After(5 * time.Second)
		fmt.Println(">>>>> 시작 >>>>>")
		defer fmt.Println("<<<<< 끝 <<<<<")
		for {
			select {
			case <-req.Context().Done():
				return nil
			case <-timer:
				return nil
			case <-ticker.C:
				data := fmt.Sprintf("data: %s(%s)\n\n", value, time.Now().Format(time.DateTime))
				fmt.Println(data)
				if _, err := fmt.Fprintf(res, data); err != nil {
					return err
				}
				res.Flush()
			}
		}
	})
	err := e.Start(fmt.Sprintf(":%d", common.ServerPort))
	if err != nil {
		panic(err)
	}
}
