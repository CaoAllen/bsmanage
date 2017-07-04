(function(){
	var as = angular.module('manageApp.site');
    as.controller('SiteManageController', SiteManageCtrl);
    SiteManageCtrl.$inject = ['$scope','$state','$rootScope','$http','$location','$translate','rowSorter','uiGridConstants','SiteSrvc'];
    function SiteManageCtrl($scope, $state, $rootScope, $http, $location, $translate, rowSorter, uiGridConstants,SiteSrvc) {
      var ctrl = this;
      ctrl.add = add;
      gridInit();
      loadData();
      
      function gridInit(){

			//rewrite sorting function to get the sorted rows, not found default ui-grid support this
			ctrl.sortByColumn = function sortByColumn(renderableRows) {
				var newRows = rowSorter.sort(this, renderableRows, this.columns);
				ctrl.listData = newRows;
			    return newRows;
			}
			
			ctrl.gridOptions = {
					appScopeProvider:ctrl,
					saveScroll: true,
					saveSelection: true,
					saveGroupingExpandedStates: true,
					enableColumnMenus: false,
					enableRowSelection: true,
					enableFullRowSelection:false,
					enableSelectAll:true,
					enableColumnResizing:true,
					multiSelect: true,
					enableRowHeaderSelection: true,
					showGridFooter:false,
					enableFooterTotalSelected:false,
					enableHorizontalScrollbar:uiGridConstants.scrollbars.NEVER,
					enableVerticalScrollbar:uiGridConstants.scrollbars.NEVER,
					paginationPageSize: 10,
					paginationPageSizes: [10],
					useExternalPagination: false,
					minRowsToShow:11,//in fact, the real will be 10(less 1 than setting) to show
					externalSort:ctrl.sortByColumn
			};
			
			ctrl.cellTemplate = "<div class=\"ui-grid-cell-contents\" title=\"TOOLTIP\" ng-click=\"grid.appScope.openDetail(row);\">{{COL_FIELD CUSTOM_FILTERS}}</div>";
			
			var twoStateSortDirectionCycle=[uiGridConstants.ASC, uiGridConstants.DESC];
			
			
			ctrl.gridOptions.columnDefs = [  
			{
	        	field: 'name', 
	        	displayName:'场地名',
	        	headerCellFilter:'translate',
	        	cellTemplate:ctrl.cellTemplate,
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'***'
			},{
	        	field: 'price', 
	        	displayName:'价格',
	        	headerCellFilter:'translate',
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'***'
			},{
	        	field: 'addressDetail', 
	        	displayName:'地址',
	        	headerCellFilter:'translate',
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'***'
			},{
	        	field: 'salesVolumn', 
	        	displayName:'销量',
	        	headerCellFilter:'translate',
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'***'
			},{
	        	field: 'status', 
	        	displayName:'状态',
	        	headerCellFilter:'translate',
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'**'
			}];
		
			ctrl.gridOptions.onRegisterApi = function (gridApi) {
				ctrl.gridApi = gridApi;
            }
      }
      
      function loadData(){
    	  SiteSrvc.getSites().then(function(data){
    		  ctrl.gridOptions.data = data; 
    	  },function(error){
    		 console.dir(error); 
    	  });
      }
      
      function add(){
    	  $state.go('siteManage.add');
      }
    }
})();