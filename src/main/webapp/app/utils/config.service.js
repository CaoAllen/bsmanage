(function() {
    angular.module('manageApp').factory('ConfigSrvc', ConfigSrvc);
    ConfigSrvc.$inject = ['$rootScope','$q','$http','$window','externalConfig'];
    function ConfigSrvc($rootScope,$q,$http,$window,urlConfig){
    	var Config = {
    			language:'zh_CN'
    	};
    	var service = {
    			getBaseUrl:getBaseUrl,
    			setLanguage:setLanguage,
    			getLanguage:getLanguage
    	};
    	
    	return service;
    	
    	function getBaseUrl(){
//    		var urlConfig = angular.injector(["ng"]).get('externalConfig');
    		if(!urlConfig || !urlConfig.baseUrl){
    			alert("not config base url!");
    		}
    		return urlConfig.baseUrl;
    	}
    	
    	function getLanguage(){
    		return Config.language;
    	}
    	
    	function setLanguage(language){
    		Config.language = language;
    	}
    }
    
})();