angular.element(function() {
	fetchConfig().then(loadConfigSuccess, loadConfigFailed);
	var filePath;

	function fetchConfig() {
		var initInjector = angular.injector(["ng"]);
		var $http = initInjector.get("$http");
		var $window = initInjector.get("$window");

		//trying to find url config file in client folder
		var pathname = $window.location.pathname;
		pathname = pathname.replace("main.html","");
		filePath = $window.location.origin + pathname + 'app/utils/' + 'config.json';

		return $http.get(filePath);
	}

	function loadConfigSuccess(response) {
		var data = response.data;
		if (data){
			console.log("Found url config in external config folder:" +  filePath);
			angular.module("manageApp").constant("externalConfig", data);
			angular.bootstrap($('body'), ['manageApp']);
		}else{
			loadConfigFailed();
		}
	}

	function loadConfigFailed() {
		alert('Failed to get client configuration!');
	}
});
