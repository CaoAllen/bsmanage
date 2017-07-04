angular.module('manageApp.utils', ['ngLocale']);
(function() {
	angular.module('manageApp.utils').factory('AppInitSrvc', function($rootScope) {
		var service = {
				appInit: appInit
		};

		return service;

		function appInit(){
			var deferred = $q.defer();
			console.log("app init... ...");
			return deferred.promise;
		}
	});
})();
