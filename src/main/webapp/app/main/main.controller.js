(function(){
	angular.module('manageApp.main').controller('MainController', MainCtrl);    
    MainCtrl.$inject = ['$q','$scope','$rootScope','$location','$translate','$state','ConfigSrvc', 'LoginSrvc'];
    
    function MainCtrl($q, $scope, $rootScope, $location, $translate, $state, ConfigSrvc, LoginSrvc) {
    	var ctrl = this;
    	ctrl.go = go;
        var load = function () {
        	$rootScope.authenticated = false;
        	
    		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams, error){
    			$rootScope.clearMessage();
    			if(!($rootScope.authenticated && $rootScope.initalLoad)){
					loadServiceContext();
				}
    		});
        };
        load();
        ctrl.getLanguage = function(){
        	return ConfigSrvc.getLanguage();
        };
        
        ctrl.setLanguage = function (lang) {
			switch(lang){
				case 'en': $translate.use('en');break;
				case 'zh_CN': $translate.use('zh_CN');break;
				default : $translate.use('zh_CN');break;
			}
			ConfigSrvc.setLanguage(lang);
        };

        ctrl.activeWhen = function (value) {
            return value ? 'active' : '';
        };

        ctrl.path = function () {
            return $location.url();
        };
        
        ctrl.login = function () {
        	go('login');
        };

        ctrl.logout = function () {
			$rootScope.authenticated = false;
			$rootScope.initalLoad = false;
			$rootScope.userName = null;
            $location.url('/login');
        };
        
        function go(state){
        	$state.go(state);
        }
        
        function loadServiceContext(){
        	LoginSrvc.loadServiceContext().then(function(res){
        		if(res.data.username){
        			$rootScope.authenticated = true;
        			$rootScope.initalLoad = true;
        			$rootScope.userName = res.data.username;
        		}
        	},function(error){
    			$rootScope.authenticated = false;
        		console.dir(error);
        		go('login');
        	});
        }

	}
})();