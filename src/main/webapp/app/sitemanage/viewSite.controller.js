(function(){
	var as = angular.module('manageApp.site');
    as.controller('ViewSiteController', ViewSiteCtrl);
    ViewSiteCtrl.$inject = ['$scope','$state','$stateParams','SiteSrvc','ConfigSrvc','Utils','Msg'];
    function ViewSiteCtrl($scope, $state, $stateParams, SiteSrvc, ConfigSrvc, Utils, Msg) {
      var ctrl = this;
      init();
      
      function init(){
    	  if(!$stateParams.siteId || $stateParams.siteId == '@id'){
    		  $state.go("siteManage");
    		  return;
    	  }
    	  ctrl.screenLock = true;
    	  ctrl.hideAddprice = true;
    	  ctrl.hideRemoveprice = true;
    	  ctrl.hideUploadFile = true;
          ctrl.stage = 1;
          ctrl.maxYear = (new Date()).getFullYear();
          ctrl.startDate = new Date();
          ctrl.cancel = cancel;
          ctrl.next = next;
          ctrl.back = back;
          ctrl.finish = finish;

          ctrl.site = {
    		  address:{}
          };
          SiteSrvc.getSite($stateParams.siteId).then(function(res){
        	  if(res.site){
        		  ctrl.site = res.site;
        		  ctrl.site.stallTimeStart = Utils.reFormatDate(ctrl.site.stallTimeStart);
        		  ctrl.site.stallTimeEnd = Utils.reFormatDate(ctrl.site.stallTimeEnd);
        		  ctrl.site.address = res.address;
        		  ctrl.site.community = res.community;
        		  ctrl.prices = res.prices;
        		  ctrl.images = [];
        		  if(res.pictures && res.pictures.length > 0){
        			  var imgUrl = ConfigSrvc.getImgUrl();
        			  angular.forEach(res.pictures,function(item,index){
        				 if(item.path){
        					 ctrl.images.push({'src': imgUrl + item.path});
        				 } 
        			  });
        		  }
        		  
        	  }
          },function(error){
        	 console.dir(error); 
          });
          
          ctrl.images = [];
          
          ctrl.cityList = [{'code':'上海市','desc':'上海市'},{'code':'北京市','desc':'北京市'}];
      }
      
      
      function cancel(){
    	  $state.go("siteManage");
      }
      
      function next(){
    	  ctrl.stage ++;
      }
      
      function finish(){
    	  $state.go("siteManage");
      }
      
      function back(){
    	  ctrl.stage --;
      }
      
    }
})();