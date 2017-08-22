(function() {
	var globalMessage;
	angular.module('manageApp', [
	   'manageApp.login',
	   'manageApp.main',
	   'manageApp.utils',
	   'manageApp.site',
	   'manageApp.order',
       'ui.router',
       'ui.bootstrap',
       'ngResource',
       'ngSanitize',
       'pascalprecht.translate',
       'ngWebSocket',
       'bsLoadingOverlay'
     ]).run(['$rootScope','$location','$state','$stateParams','$window','$q','$http', runFun
     ]).config(['$stateProvider','$urlRouterProvider','$resourceProvider','$translateProvider', '$httpProvider','$rootScopeProvider',configFun
     ]).filter('trusted', ['$sce', function ($sce) {
		return function(url) {
			return $sce.trustAsResourceUrl(url);
		};
	}]);
	
	function runFun($rootScope, $location,$state,$stateParams,$window,$q,$http,ConfigSrvc){
        //make current message accessible to root scope and therefore all scopes
        $rootScope.getMessage = function () {
            return globalMessage;
        };
        $rootScope.setMessage = function (message) {
        	globalMessage = message;
        };
        $rootScope.clearMessage = function () {
        	globalMessage = null;
        };

        //$rootScope.$on('$viewContentChange', funtion());
        //check the networking connection.

        $http.get('api/ping')
                .success(function (data) {
                    console.log("ping result@"+data);
                })
                .error(function (data) {
                     $rootScope.setMessage({text:'Network connection eror!', type:'danger', show:true});
                });
	}
	
	function configFun($stateProvider, $urlRouterProvider, $resourceProvider, $translateProvider, $httpProvider, $rootScopeProvider) {
   		$rootScopeProvider.digestTtl(20); 		   
        // Translate
        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/locale_',
            suffix: '.json'
        });
        $translateProvider.preferredLanguage('zh_CN'); 
        
        // Authentication interceptor
        $httpProvider.interceptors.push(function($q, $injector) {
       	 return {
       		 responseError: function(rejection) {
       			 if (rejection.status === 401 && rejection.config.byPass401Interceptor !== true ) {
       				 window.location.href=window.location.origin+window.location.pathname+"#/login";
       			 }
       			 return $q.reject(rejection);
       		 }
       	 }
        });   
        
        //configure $http to catch message responses and show them
        $httpProvider.interceptors.push(function ($q) {
            var setMessage = function (response) {
                //if the response has a text and a type property, it is a message to be shown
                if (response.data.text && response.data.type) {
                	globalMessage = {
                        text: response.data.text,
                        type: response.data.type,
                        show: true
                    };
                }
            };

            return {
                //this is called after each successful server request
                'response': function (response) {
                    // console.log('request:' + response);
                    setMessage(response);
                    return response || $q.when(response);
                },
                //this is called after each unsuccessful server request
                'responseError': function (response) {
                    console.dir(response);
                    setMessage(response);
                    return $q.reject(response);
                }

            };
        });

        $httpProvider.defaults.withCredentials = true;  
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        // Don't strip trailing slashes from calculated URLs
        $resourceProvider.defaults.stripTrailingSlashes = false;

        // For any unmatched url, redirect to /application   
        $urlRouterProvider.otherwise('home'); 
                    
        $stateProvider.state('home', {
            url: "/home",
            views: {
               'maincontent@': {
              	templateUrl: "app/home/home.html",
  				controller: 'HomeController',
  				controllerAs: 'ctrl'  
               }       
            }
         }).state('login', {
			data: { authenticated: true },
			url: "/login",
			views: {
				'preinitcontent@': {
					templateUrl: "app/login/login.html",
					controller: 'LoginController as ctrl'
				}
			}
     	}).state('siteManage', {
	         url: "/siteManage",
	         views: {
	            'maincontent@': {
	               templateUrl: "app/sitemanage/site.html",
	               controller: 'SiteManageController as ctrl'
	            }
	         }
     	}).state('orderManage', {
	         url: "/orderManage",
	         views: {
	            'maincontent@': {
	               templateUrl: "app/ordermanage/order.html",
	               controller: 'OrderManageController as ctrl'
	            }
	         }
     	});
	}
})();