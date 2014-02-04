import org.vertx.groovy.core.buffer.Buffer
import org.vertx.java.core.json.JsonObject

def config = container.config

def clients = []

def dataHandler = { message ->
	def json = new JsonObject(message.body).toString()
	println json
	clients.each { ws -> ws.writeTextFrame json }
}

vertx.eventBus.registerHandler(config.address, dataHandler)

def server = vertx.createHttpServer()
server.websocketHandler { ws ->

	if(!(ws.path == "/data")) {
		ws.reject()
	} else {
		clients += ws
		ws.endHandler { clients -= ws }
	}
}.listen(config.port, config.host)




