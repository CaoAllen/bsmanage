(function(){
	var as = angular.module('manageApp');
    as.controller('HomeController', HomeCtrl);
    HomeCtrl.$inject = ['$scope','$rootScope','$http','$location','$translate'];
    function HomeCtrl($scope, $rootScope, $http, $location, $translate) {
      var ctrl = this;
      
    }
})();