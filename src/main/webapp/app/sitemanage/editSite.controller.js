(function(){
	var as = angular.module('manageApp.site');
    as.controller('EditSiteController', EditSiteController);
    EditSiteController.$inject = ['$scope','$state','$stateParams','SiteSrvc','ConfigSrvc','Utils','Msg','fileReader'];
    function EditSiteController($scope, $state, $stateParams, SiteSrvc, ConfigSrvc, Utils, Msg, fileReader) {
    	var ctrl = this;
        init();
        
        function init(){
      	  	if(!$stateParams.siteId || $stateParams.siteId == '@id'){
      	  		$state.go("siteManage");
      	  		return;
      	  	}
            ctrl.stage = 1;
            ctrl.maxYear = (new Date()).getFullYear();
            ctrl.startDate = new Date();
            
            ctrl.cancel = cancel;
            ctrl.next = next;
            ctrl.back = back;
            ctrl.finish = finish;
            ctrl.addPrice = addPrice;
            ctrl.removePrice = removePrice;
            
            ctrl.site = {
      		  community:{}
            };
            loadSite();
            
            ctrl.uploadImg = uploadImg;
            ctrl.deleteImg = deleteImg;
            
            ctrl.cityList = [{'code':'上海市','desc':'上海市'},{'code':'北京市','desc':'北京市'}];
            ctrl.cityChange = cityChange;
            ctrl.districtList = SiteSrvc.getDistricts();
        }

        function loadSite(){
            SiteSrvc.getSite($stateParams.siteId).then(function(res){
          	  if(res.site){
          		  ctrl.site = res.site;
          		  ctrl.site.id = res.site.siteId;
          		  ctrl.site.stallTimeStart = Utils.reFormatDate(ctrl.site.stallTimeStart);
          		  ctrl.site.stallTimeEnd = Utils.reFormatDate(ctrl.site.stallTimeEnd);
          		  ctrl.site.address = res.address;
          		  if(res.community){
              		  ctrl.site.community = res.community;
          		  }else ctrl.site.community = {};
          		  ctrl.site.community.siteId = res.site.siteId;
          		  if(res.prices){
              		  ctrl.prices = res.prices;
          		  }else {
          			  ctrl.prices = [];
          			  addPrice();
          		  }
          		  ctrl.images = [];
          		  if(res.pictures && res.pictures.length > 0){
          			  var imgUrl = ConfigSrvc.getImgUrl();
          			  angular.forEach(res.pictures,function(item,index){
          				 if(item.path){
          					 ctrl.images.push({
          						 pictureId: item.pictureId,
          						 src: imgUrl + item.path,
          						 uploadBtn:'site.picture.uploaded',
          						 isUploaded:true
          					 });
          				 } 
          			  });
          		  }
                  ctrl.unbingWatch = $scope.$watch('ctrl.site',function(newValue,oldValue){
                	  if(newValue != oldValue){
                    	  ctrl.siteChanged = true;
                	  }
                  },true);
          	  }
            },function(error){
          	 console.dir(error); 
            });
        }
        
        $scope.getFile = function () {
            fileReader.readAsDataUrl(ctrl.myImg, $scope).then(function(result) {
                var data = new FormData();
                data.append('file', ctrl.myImg);//single file
                //TODO user really siteId
                data.append('siteId', ctrl.site.id);
                
          	  ctrl.images.push({
          		  src: result,
          		  data: data,
          		  uploadBtn:'site.picture.upload',
          		  isUploaded:false
      		  });
          	  ctrl.myImg = null;
            },function(error){
          	  console.dir(error);
            });
        };
        
        function cancel(){
      	  $state.go("siteManage");
        }
        
        function next(){
      	  Msg.clear();
      	  switch(ctrl.stage){
      	  	case 1: {
      	  		console.dir(ctrl.site);
      	  		if(!validateSite()){
      	  			return;
      	  		}
      	  		if(!ctrl.siteChanged){
  	  				ctrl.stage ++;
  	  				return;
      	  		}
  	  			SiteSrvc.updateSite(ctrl.site).then(function(res){
      	  			if(res.status){
      	  				ctrl.stage ++;
      	  				ctrl.siteChanged = false;
      	  				Msg.show('场地更新成功!','S',true);
      	  			}
      	  		},function(error){
      	  			console.dir(error);
  	  				Msg.show('场地更新失败!','D',true);
      	  		});
      	  		break;
      	  	};
      	  	case 2: {
      	  		//TODO validation
      	  		if(!ctrl.siteChanged){
  	  				ctrl.stage ++;
  	  				return;
      	  		}
  	  			SiteSrvc.updateCommunity(ctrl.site.community).then(function(res){
      	  			if(res.status){
      	  				ctrl.siteChanged = false;
      	  				Msg.show('update success!','S',true);
      	  				ctrl.stage ++;
      	  			}else {
      	  				Msg.show('update failed!','D',true);
      	  			}
      	  		},function(error){
      	  			console.dir(error);
  	  				Msg.show('update failed!','D',true);
      	  		});
      	  		break;
      	  	};
      	  	case 3: {
      	  		ctrl.unbingWatch();
    	  		if(!SiteSrvc.commonValidatePrice(ctrl.prices)){
    	  			return;
    	  		}
      	  		SiteSrvc.updatePrice(ctrl.prices, ctrl.site.id).then(function(res){
  	  				Msg.show('价格修改成功!','S',true);
          	  		ctrl.stage ++;
      	  		},function(error){
      	  			console.dir(error);
      	  		});
      	  		break;
      	  	};
      	  }
        }
        
        function finish(){
      	  if(ctrl.images.length === 0){
      		  Msg.show('请至少上传一张图片！','W',true);
      		  return;
      	  }
      	  var continued = true;
      	  angular.forEach(ctrl.images,function(item,index){
      		  if(continued && !item.isUploaded){
      			  continued = false;
      		  }
      	  });
      	  if(ctrl.images.length > 0 && continued){
      		  if(ctrl.site.status == 'F'){
      			 $state.go("siteManage");
      			 return;
      		  }
      		  SiteSrvc.finishSite(ctrl.site.id).then(function(res){
      			  if(res.status){
      	        	  Msg.showModal('','场地修改成功！').result.then(function(code) {
      	  				if(code == 'ok'){
      	    	    		  $state.go("siteManage");
      	  				}
  					  });
      			  }else{
      				  Msg.show('场地修改失败!','D',true);
      			  }
      		  },function(error){
    	  			console.dir(error);
      		  });
      	  }else{
      		  Msg.show('请上传或者删除未上传的图片!','W',true);
      		  return;
      	  }
        }
        
        function validateSite(){
      	  if(!ctrl.site || ctrl.site.id == null){
      		  Msg.show('系统错误','W',true);
      		  return false;
      	  }
      	  if(!SiteSrvc.commonValidateSite(ctrl.site)){
      		  return false;
      	  }
      	  return true;
        }
        
        function back(){
      	  ctrl.stage --;
        }
        
        function addPrice(){
            var basePrice = {
              	  amount:0,
              	  stallSize:'',
              	  timeUnit:'',
              	  specialCategory:''
            };
      	  ctrl.prices.push(basePrice);
        }
        
        function removePrice(i){
      	  ctrl.prices.splice(i, 1);
        }
        
        function uploadImg(i){
      	  ctrl.images[i].uploadBtn = 'site.picture.uploading';
      	  console.dir(ctrl.images[i]);
      	  SiteSrvc.uploadPicture(ctrl.images[i].data).then(function(res){
      		  console.dir(res);
      		  if(res.data && res.data.status){
          		  Msg.show('Upload succesfully!','S',true);
      	    	  ctrl.images[i].uploadBtn = 'site.picture.uploaded';
      	    	  ctrl.images[i].pictureId = res.data.object.pictureId;
      	    	  ctrl.images[i].isUploaded = true;
      		  }else {
          		  Msg.show('Upload failed!','D',true);
      	    	  ctrl.images[i].uploadBtn = 'site.picture.upload';
      		  }
      	  },function(error){
      		  console.dir(error);
      		  Msg.show('Upload failed!','D',true);
  	    	  ctrl.images[i].uploadBtn = 'site.picture.upload';
      	  });
        }

        function deleteImg(i){
      	  var pictureId = ctrl.images[i].pictureId;
      	  if(pictureId){
          	  SiteSrvc.deletePicture(pictureId).then(function(res){
          		  if(res.data && res.data.status){
              		  Msg.show('delete succesfully!','S',true);
          		  }
          	  });
      	  }
      	  ctrl.images.splice(i, 1);
        }
        
        
        function cityChange(){
      	  switch(ctrl.site.address.city){
  	    	  case '北京市': 
  	        	  ctrl.districtList = [{'code':'昌平区','desc':'昌平区'},{'code':'海淀区','desc':'海淀区'}];
  	        	  break;
  	    	  case '上海市': 
  	    		  ctrl.districtList = SiteSrvc.getDistricts();
  	        	  break;
      	  }
        }
      }
})();