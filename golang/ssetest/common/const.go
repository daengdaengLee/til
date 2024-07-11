package common

import "fmt"

var (
	ServerPort = 8080
	ServerUrl  = fmt.Sprintf("http://localhost:%d", ServerPort)

	ProxyPort = 8081
	ProxyUrl  = fmt.Sprintf("http://localhost:%d", ProxyPort)
)
