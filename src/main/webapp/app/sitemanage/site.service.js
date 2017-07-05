(function() {
    angular.module('manageApp.site').factory('SiteSrvc', SiteSrvc);
    SiteSrvc.$inject = ['$rootScope','$q','$resource','$http','$window','ConfigSrvc'];
    function SiteSrvc($rootScope,$q,$resource,$http,$window,ConfigSrvc){
    	var service = {
    			getSites:getSites,
    			addSite:addSite,
    			updateSite:updateSite,
    			finishSite:finishSite,
    			addCommunity:addCommunity,
    			updateCommunity:updateCommunity,
    			addPrice:addPrice,
    			deletePrice:deletePrice,
    			uploadPicture:uploadPicture,
    			deletePicture:deletePicture
    	};
    	return service;
    	
    	function getSites(search){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/list', {}, {
	    		getSites : {
					method : 'POST',
				    headers: {
				    	'Accept':'application/json, text/plain, */*;charset="UTF-8"',
				        'Content-Type': 'application/x-www-form-urlencoded',
				        'charset':'UTF-8'
				    },
					params : {
				        'name': "",
				        'pageNo': 0,
				        'pageSize': 10,
				        'sortName': 'updateTime',
				        'sortDirection': 'DESC'
					},
					isArray : true
				}
			});
	    	return resource.getSites().$promise;
    	}
    	
    	function addSite(siteForm){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/add', {}, {
	    		addSite : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.addSite(siteForm).$promise;
    	}
    	
    	function updateSite(){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/update', {}, {
	    		updateSite : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.updateSite(siteForm).$promise;
    	}
    	
    	function finishSite(siteId){
    		var data = new FormData();
    		data.append('siteId', siteId);
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/finish', {}, {
	    		finishSite : {
					method : 'POST',
		            transformRequest: angular.identity,
		            headers: {
		                'Content-Type': undefined
		            },
					params : {
					},
					isArray : false
				}
			});
	    	return resource.finishSite(data).$promise;
    	}
    	
    	function addCommunity(community){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/community/add', {}, {
	    		addCommunity : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.addCommunity(community).$promise;
    	}
		
    	function updateCommunity(community){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/community/update', {}, {
	    		updateCommunity : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.updateCommunity(community).$promise;
    	}
		
    	function addPrice(prices,siteId){
    		var data = new FormData();
    		data.append('prices',angular.toJson(prices));
    		data.append('siteId',siteId);
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/price/add', {}, {
	    		addPrice : {
					method : 'POST',
		            transformRequest: angular.identity,
		            headers: {
		                'Content-Type': undefined
		            },
					params : {
					},
					isArray : false
				}
			});
	    	return resource.addPrice(data).$promise;
    	}
    	
		function deletePrice(id){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/price/delete', {}, {
	    		deletePrice : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.deletePrice({'id':id}).$promise;
		}
		
		function uploadPicture(data){
	        return $http({
	            method:'POST',
	            url: ConfigSrvc.getBaseUrl()+'/site/picture/upload',
	            data: data,
	            headers: {'Content-Type':undefined},
	            transformRequest: angular.identity
	          });
		}
		
		function deletePicture(pictureId){
    		var data = new FormData();
    		data.append('pictureId',pictureId);
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/picture/delete', {}, {
	    		deletePicture : {
					method : 'POST',
		            headers: {'Content-Type':undefined},
		            transformRequest: angular.identity,
					params : {
					},
					isArray : false
				}
			});
	    	return resource.deletePicture(data).$promise;
		}
    }
    
})();