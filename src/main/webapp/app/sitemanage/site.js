(function(){
   angular.module('manageApp.site',[
		'ui.router',
		'ui.bootstrap',
		'ui.bootstrap.timepicker',
		'ui.grid',
		'ui.grid.pagination',
		'ui.grid.autoResize',
		'ui.mask',
		'ngResource'
   ]).config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('siteManage.add',{
			url: "/add",
			views: {
				'maincontent@': {
					templateUrl:"app/sitemanage/addSite.html",
	      			controller: 'AddSiteController as ctrl'
	      		 }
			}
		});
	}]);
})();