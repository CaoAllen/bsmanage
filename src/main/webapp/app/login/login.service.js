(function() {
    angular.module('manageApp.login').factory('LoginSrvc', LoginSrvc);
    LoginSrvc.$inject = ['$rootScope','$q','$http','$window','base64','ConfigSrvc'];
    function LoginSrvc($rootScope,$q,$http,$window,base64,ConfigSrvc){
    	var service = {
    			login: login,
    			logout: logout,
    			loadServiceContext: loadServiceContext
    	};
    	
    	return service;
    	
		function login(userName, password){
			var headers = (userName && password) ? {
				'Authorization' : "Basic " + base64.encode(userName + ":"+ password)
			} : {};
			return $http({
    			method: 'POST',
    			url: ConfigSrvc.getBaseUrl() + '/login',
    			headers : headers,
    			byPass401Interceptor: true
			});
		}
		
		function logout(){
			
		}
		
		function loadServiceContext(){
			var appUrl = ConfigSrvc.getBaseUrl();
			return $http({
    			method: 'POST',
    			url: appUrl+'/context'
			});
		}
    }
    
})();