(function(){
	var as = angular.module('manageApp.order');
    as.controller('OrderManageController', OrderManageCtrl);
    OrderManageCtrl.$inject = ['$scope','$state','$rootScope','$http','$location','$translate','rowSorter','uiGridConstants','SiteSrvc','Msg'];
    function OrderManageCtrl($scope, $state, $rootScope, $http, $location, $translate, rowSorter, uiGridConstants,SiteSrvc,Msg) {
      var ctrl = this;
      ctrl.search = search;
      ctrl.reset = reset;
      ctrl.add = add;
      ctrl.goDetail = goDetail;
      ctrl.goEdit = goEdit;
      ctrl.remove = remove;
      ctrl.disable = disable;
      gridInit();
      loadData();
      
      function gridInit(){

			//rewrite sorting function to get the sorted rows, not found default ui-grid support this
			function sortByColumn(renderableRows) {
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
					externalSort:sortByColumn
			};
			
			ctrl.showStatus = function(status){
				if(status == 'F'){
					return "完成";
				}
				return "未完成";
			}

			ctrl.isFinish = function(status){
				return status == 'F';
			}
			var actionCellTemplate = "<div class=\"ui-grid-cell-contents \" style=\"text-align: center\" title=\"TOOLTIP\">" +
					"<a href='javascript:void(0)' title='修改' ng-click=\"grid.appScope.goEdit(row.entity.siteId);\"><i class='glyphicon glyphicon-edit'></i></a>&nbsp;&nbsp;&nbsp;" +
					"<a href='javascript:void(0)' title='禁用' ng-click=\"grid.appScope.disable(row.entity.siteId);\"><i class='glyphicon glyphicon-ban-circle'></i></a>&nbsp;&nbsp;&nbsp;" +
					"<a href='javascript:void(0)' title='删除' ng-click=\"grid.appScope.remove(row.entity.siteId);\"><i class='glyphicon glyphicon-remove'></i></a></div>";
			var nameCellTemplate = "<div class=\"ui-grid-cell-contents\" title=\"TOOLTIP\"><a href='javascript:void(0)'  ng-click=\"grid.appScope.goDetail(row.entity.siteId);\">{{COL_FIELD CUSTOM_FILTERS}}</a></div>";
			var statusCellTemplate = "<div class=\"ui-grid-cell-contents\" title=\"TOOLTIP\"><span style=\"{{grid.appScope.isFinish(row.entity.status)?'':'color:red'}}\">{{grid.appScope.showStatus(row.entity.status)}}</span></div>";
			
			var twoStateSortDirectionCycle=[uiGridConstants.ASC, uiGridConstants.DESC];
			
			ctrl.gridOptions.columnDefs = [{
	        	field: 'action', 
	        	displayName:'操作',
	        	headerCellFilter:'translate',
	        	cellTemplate: actionCellTemplate,
     			width:'*'
			},  
			{
	        	field: 'name', 
	        	displayName:'场地名',
	        	headerCellFilter:'translate',
	        	cellTemplate: nameCellTemplate,
	        	sortDirectionCycle: twoStateSortDirectionCycle,
     			width:'***'
			},{
	        	field: 'amount', 
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
	        	field: 'statusName', 
	        	displayName:'状态',
	        	headerCellFilter:'translate',
	        	sortDirectionCycle: twoStateSortDirectionCycle,
	        	cellTemplate: statusCellTemplate,
     			width:'**'
			}];
		
			ctrl.gridOptions.onRegisterApi = function (gridApi) {
				ctrl.gridApi = gridApi;
            }
      }
      
      function loadData(params){
    	  SiteSrvc.getSites(params).then(function(data){
    		  ctrl.gridOptions.data = data;
    		  angular.forEach(ctrl.gridOptions.data,function(item,index){
    			  if(item.status == 'F'){
	    			 item.statusName = '完成';
    			  }else item.statusName = '未完成';
    		  });
    	  },function(error){
    		 console.dir(error); 
    	  });
      }
      
      function search(){
    	  loadData(ctrl.searchParams);
      }

      function reset(){
    	  ctrl.searchParams = {};
    	  loadData();
      }
      
      function add(){
    	  $state.go('siteManage.add');
      }
      
      function goDetail(siteId){
    	  $state.go('siteManage.view',{'siteId':siteId});
      }
      
      function goEdit(siteId){
    	  $state.go('siteManage.edit',{'siteId':siteId});
      }
      
      function remove(siteId){
    	  Msg.showModal('','确定删除此场地吗?','confirm').result.then(function(code) {
  				if(code == 'ok'){
  					SiteSrvc.deleteSite(siteId).then(function(){
  						Msg.show('删除成功','S',true);
  						loadData();
  					},function(error){
  						console.dir(error); 
					})	
  				}
		  });
      }
      

      function disable(siteId){
    	  Msg.showModal('','确定禁用此场地吗?','confirm').result.then(function(code) {
  				if(code == 'ok'){
  					SiteSrvc.disableSite(siteId).then(function(){
  						Msg.show('修改成功','S',true);
  						loadData();
  					},function(error){
  						console.dir(error); 
  					})	
  				}
  		  });
      }
    }
})();