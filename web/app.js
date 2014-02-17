var app = angular.module("map-test", [ "google-maps" ]);

app.factory("carService", function($rootScope) {

	if ("WebSocket" in window) {
		var service = {};
		var ws = new WebSocket("ws://localhost:3333/data");

		ws.onmessage = function(message) {
			$rootScope.$apply(function() {
				service.callback(JSON.parse(message.data));
			});
		};

		service.subscribe = function(callback) {
			service.callback = callback;
		};

		return service;
	} else {
		alert("No Websocket support in your browser!");
	}
});

function MainCtrl($scope, carService) {

	$scope.cars = [];

	carService.subscribe(function(newCar) {
		newCar.icon = "circle-green.png";
		_.each($scope.cars, function(oldCar, index) {
			if (oldCar.id == newCar.id) {
				$scope.cars.splice(index, 1);
			}
		});
		$scope.cars.push(newCar);
		// console.log(JSON.stringify(newCar));
	});

	$scope.map = {
		center : {
			latitude : 53.542666,
			longitude : 9.985268
		},
		zoom : 15,
		options : {
			mapTypeControl : true
		}
	};

	$scope.carClick = function(car) {
		alert("Car " + car.id + " has been clicked at " + car.coords.latitude + ", " + car.coords.longitude
				+ ". Map center is " + $scope.map.center.latitude + ", " + $scope.map.center.longitude);
	};
}