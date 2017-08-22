(function(){
   angular.module('manageApp.order',[
		'ui.router',
		'ui.bootstrap',
		'ui.bootstrap.timepicker',
		'ui.grid',
		'ui.grid.pagination',
		'ui.grid.autoResize',
		'ui.mask',
		'ngResource'
   ]).config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('orderManage.edit',{
			url: "/edit",
			views: {
				'maincontent@': {
					templateUrl:"app/ordermanage/editOrder.html",
	      			controller: 'EditOrderController as ctrl'
	      		 }
			},
			params: {'orderId':'@id'}
		});
		$stateProvider.state('orderManage.view',{
			url: "/view",
			views: {
				'maincontent@': {
					templateUrl:"app/ordermanage/viewOrder.html",
	      			controller: 'ViewOrderController as ctrl'
	      		 }
			},
			params: {'orderId':'@id'}
		});
	}]);
})();