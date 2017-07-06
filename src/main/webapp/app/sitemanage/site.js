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
		$stateProvider.state('siteManage.edit',{
			url: "/edit",
			views: {
				'maincontent@': {
					templateUrl:"app/sitemanage/editSite.html",
	      			controller: 'EditSiteController as ctrl'
	      		 }
			},
			params: {'siteId':'@id'}
		});
		$stateProvider.state('siteManage.view',{
			url: "/view",
			views: {
				'maincontent@': {
					templateUrl:"app/sitemanage/viewSite.html",
	      			controller: 'ViewSiteController as ctrl'
	      		 }
			},
			params: {'siteId':'@id'}
		});
	}]);
})();