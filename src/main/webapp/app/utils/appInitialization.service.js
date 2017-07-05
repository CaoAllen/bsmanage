(function() {
	angular.module('manageApp.utils').factory('AppInitSrvc', function($rootScope,$q) {
		var service = {
				appInit: appInit
		};

		return service;

		function appInit(){
			var deferred = $q.defer();
			console.log("app init... ...");
			
			//trim function in IE9
			if(typeof String.prototype.trim !== 'function') {
				  String.prototype.trim = function() {
				    return this.replace(/^\s+|\s+$/g, ''); 
				  }
			}
			return deferred.promise;
		}
	});
})();
