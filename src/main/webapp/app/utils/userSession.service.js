(function() {
	var as = angular.module('manageApp.utils');
	as.factory('UserSessionSrvc', function($rootScope, $filter) {
		var userInfo = {
				
		};
		var service = {
				getUserInfo : getUserInfo
		};

		return service;
        
		function getUserInfo(){
			return userInfo;
		}
	});
})();
