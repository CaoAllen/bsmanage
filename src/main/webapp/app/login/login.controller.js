(function(){
	angular.module('manageApp.login').controller('LoginController', LoginCtrl);
    LoginCtrl.$inject = ['$scope','$rootScope','$http','$location','$state','LoginSrvc'];
    
    function LoginCtrl($scope, $rootScope, $http, $location, $state, LoginSrvc) {
    	var ctrl = this;
    	
    	ctrl.login = function () {
			if(ctrl.status == 'login'){
				return;
			}
			ctrl.status = 'login';
    		LoginSrvc.login(ctrl.userName,ctrl.password).then(function(data){
    			console.dir(data);
				ctrl.status = 'success';
    			$rootScope.authenticated = true;
    			$rootScope.initalLoad = true;
    			$rootScope.userName = data.data.username;
    			$state.go('home');
    		}).catch(function(response){
				ctrl.status = 'failed';
    			if(response.status == 401){
    				$rootScope.setMessage({text:'User name or password is invalid!', type:'danger', show:true});
    			}
    		});
        };
        
    }
})();