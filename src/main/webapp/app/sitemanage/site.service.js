(function() {
    angular.module('manageApp.site').factory('SiteSrvc', SiteSrvc);
    SiteSrvc.$inject = ['$rootScope','$q','$resource','$http','$window','ConfigSrvc','Msg','Utils'];
    function SiteSrvc($rootScope,$q,$resource,$http,$window,ConfigSrvc,Msg,Utils){
    	var service = {
    			getSites:getSites,
    			getSite:getSite,
    			addSite:addSite,
    			updateSite:updateSite,
    			finishSite:finishSite,
    			addCommunity:addCommunity,
    			updateCommunity:updateCommunity,
    			addPrice:addPrice,
    			updatePrice:updatePrice,
    			deletePrice:deletePrice,
    			uploadPicture:uploadPicture,
    			deletePicture:deletePicture,
    			commonValidateSite:commonValidateSite,
    			commonValidatePrice:commonValidatePrice,
    			getDistricts:getDistricts
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
    	
    	function getSite(siteId){
    		var data = new FormData();
    		data.append('siteId', siteId);
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/get', {}, {
	    		getSite : {
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
	    	return resource.getSite(data).$promise;
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
    	
    	function updateSite(site){
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/update', {}, {
	    		updateSite : {
					method : 'POST',
					params : {
					},
					isArray : false
				}
			});
	    	return resource.updateSite(site).$promise;
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

    	function updatePrice(prices,siteId){
    		var data = new FormData();
    		data.append('prices',angular.toJson(prices));
    		data.append('siteId',siteId);
	    	var resource = $resource(ConfigSrvc.getBaseUrl()+'/site/price/update', {}, {
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
		
		function commonValidateSite(site){
			if(Utils.isTrimEmpty(site.name)){
				Msg.show('场地名不能为空','W',true);
				return false;
      	  	}
      	  	if(Utils.isNumberEmpty(site.flowrate)){
      	  		Msg.show('人流量不能为空','W',true);
      	  		return false;
      	  	}
      	  	if(site.address == null){
      	  		Msg.show('地址不能为空','W',true);
      	  		return false;
      	  	}
      	  	if(Utils.isTrimEmpty(site.address.city)){
      	  		Msg.show('城市不能为空','W',true);
      	  		return false;
      	  	}
      	  	if(Utils.isTrimEmpty(site.address.district)){
      	  		Msg.show('地区不能为空','W',true);
      	  		return false;
      	  	}
      	  	if(Utils.isTrimEmpty(site.address.addressDetail)){
      	  		Msg.show('详细地址不能为空','W',true);
      	  		return false;
      	  	}
      	  	//TODO more
      	  	return true;
		}
		
		function commonValidatePrice(prices){
			if(prices == null || (prices instanceof Array && prices.length == 0)){
				Msg.show('请输入至少一条价格信息','W',true);
      	  		return false;
			}
			var continued = true;
			angular.forEach(prices,function(item,index){
				if(continued){
					var prefix = "第"+(index + 1)+"条价格信息：";
					if(Number(item.amount) <= 0){
						Msg.show(prefix + '价格数目不能为空','W',true);
						continued = false;
					}
					if(Utils.isStringEmpty(item.stallSize)){
						Msg.show(prefix + '场地大小不能为空','W',true);
						continued = false;
					}
					if(Utils.isStringEmpty(item.timeUnit)){
						Msg.show(prefix + '时间单位不能为空','W',true);
						continued = false;
						return false;
					}
				}
			});
			return continued;
		}
		
		function getDistricts(){
			var districts = ["黄浦区","徐汇区","长宁区","静安区","普陀区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区"];
			var result = [];
			angular.forEach(districts,function(item,index){
				result.push(createDistrict(item));
			});
			return result;
		}
		
		function createDistrict(name){
			return {'desc':name,'code':name};
		}
    }
    
})();