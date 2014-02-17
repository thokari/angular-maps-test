def webServerConf = [
	"web_root": "../web",
]

def webSocketServerConf = [
	"address": "websocket.server",
	"port": 3333,
]

def testDuration = 60 // seconds
def carCount = 100
def carConfig = [interval: 5000]

container.with {
	deployModule("io.vertx~mod-web-server~2.0.0-final", webServerConf)
	deployVerticle("websocket-server.groovy", webSocketServerConf)

	deployVerticle("car.groovy", carConfig, carCount)
}

vertx.setTimer(testDuration * 1000) { container.exit() }

