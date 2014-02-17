def config = container.config
def rng = new Random()

def car = init(rng)

vertx.setTimer(rng.nextInt(config.interval) + 1) {
	vertx.setPeriodic(config.interval) {
		vertx.eventBus.send("websocket.server", car)
	}
}

vertx.setPeriodic(rng.nextInt(500) + 500) {
	car.coords.with {
		def changeLat = rng.nextGaussian() / 3000
		def changeLong = rng.nextGaussian() / 3000
		latitude += changeLat
		longitude += changeLong
	}
}

def init(rng) {
	def id = UUID.randomUUID()
	def coords = [
		latitude : 53.542666 + rng.nextDouble() / 100,
		longitude : 9.985268 + rng.nextDouble() / 100
	]
	[id: id, coords: coords]
}