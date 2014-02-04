def webServerConf = [
	"web_root": "../web",
	"host": "localhost"
]

def webSocketServerConf = [
	"address": "websocket.server",
	"port": 3333,
	"host": "localhost"
]

def carCount = 300
def carConfig = [interval: 3000]

container.with {
	deployModule("io.vertx~mod-web-server~2.0.0-final", webServerConf)
	deployVerticle("websocket-server.groovy", webSocketServerConf)

	deployVerticle("car.groovy", carConfig, carCount)
}

