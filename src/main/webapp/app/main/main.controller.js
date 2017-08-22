(function(){
	angular.module('manageApp.main').controller('MainController', MainCtrl);    
    MainCtrl.$inject = ['$q','$scope','$rootScope','$location','$translate','$state','ConfigSrvc', 'LoginSrvc','AppInitSrvc','Msg'];
    
    function MainCtrl($q, $scope, $rootScope, $location, $translate, $state, ConfigSrvc, LoginSrvc, AppInitSrvc, Msg) {
    	var ctrl = this;
    	ctrl.go = go;
        var load = function () {
        	$rootScope.authenticated = false;
        	
    		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams, error){
    			$rootScope.clearMessage();
    			//should invoke when use refresh browser
    			if((fromState.name == '' ||  fromState.url == '^') && !isInLogin(toState)){
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
	  	  	Msg.showModal('','确定退出吗?','confirm').result.then(function(code) {
  	  			if(code == 'ok'){
  	  				go('login');
				}
	  	  	});
        };
        
        function go(state){
        	$state.go(state,{},{reload:true});
        }
        
        function loadServiceContext(){
        	LoginSrvc.loadServiceContext().then(function(res){
        		if(res.data.username){
        			$rootScope.authenticated = true;
        			$rootScope.userName = res.data.username;
        			AppInitSrvc.appInit().then(function(){
            			$rootScope.initalLoad = true;
        			});
        		}
        	},function(error){
        		console.dir(error);
        		if(!isInLogin()){
        			go('login');
        		}
        	});
        }
        
        function isInLogin(state){
        	if(state == undefined) state = $state.current;
        	return state.name == 'login' && state.url == '/login';
        }

	}
})();