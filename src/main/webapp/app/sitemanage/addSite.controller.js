(function(){
	var as = angular.module('manageApp.site');
    as.controller('AddSiteController', AddSiteCtrl);
    AddSiteCtrl.$inject = ['$scope','$state','$rootScope','fileReader','SiteSrvc','Utils','Msg'];
    function AddSiteCtrl($scope, $state, $rootScope,fileReader, SiteSrvc, Utils, Msg) {
      var ctrl = this;
      init();
      
      function init(){
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
          ctrl.prices = [];
          addPrice();
          
          ctrl.images = [];
          ctrl.uploadImg = uploadImg;
          ctrl.deleteImg = deleteImg;
          
          ctrl.cityList = [{'code':'上海市','desc':'上海市'},{'code':'北京市','desc':'北京市'}];
          ctrl.cityChange = cityChange;
      }

      
      $scope.getFile = function () {
          fileReader.readAsDataUrl(ctrl.myImg, $scope).then(function(result) {
              var data = new FormData();
              data.append('file', ctrl.myImg);//single file
              //TODO user really siteId
              data.append('siteId', 1);
              
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
    	  	//add site
    	  	case 1: {
    	  		console.dir(ctrl.site);
    	  		if(!validateSite()){
    	  			return;
    	  		}
    	  		if(!ctrl.site.id){
    	  			SiteSrvc.addSite(ctrl.site).then(function(res){
        	  			if(res.siteId > 0){
        	  				ctrl.site.id = res.siteId;
        	  				Msg.show('add success!','S',true);
        	  				ctrl.stage ++;
        	  			}
        	  		},function(error){
        	  			console.dir(error);
        	  		});
    	  		}else{
    	  			SiteSrvc.updateSite(ctrl.site).then(function(res){
        	  			if(res.data){
        	  				ctrl.stage ++;
        	  				Msg.show('update success!','S',true);
        	  			}
        	  		},function(error){
        	  			console.dir(error);
        	  		});
    	  		}
    	  		break;
    	  	};
    	  	//add community
    	  	case 2: {
    	  		ctrl.site.community.siteId = ctrl.site.id;
	  			SiteSrvc.addCommunity(ctrl.site.community).then(function(res){
    	  			if(res.status){
    	  				Msg.show('add success!','S',true);
    	  				ctrl.stage ++;
    	  			}else {
    	  				Msg.show('add failed!','D',true);
    	  			}
    	  		},function(error){
    	  			console.dir(error);
    	  		});
    	  		break;
    	  	};
    	  	//add price
    	  	case 3: {
    	  		SiteSrvc.addPrice(ctrl.prices, ctrl.site.id).then(function(res){
	  				Msg.show('价格添加成功!','S',true);
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
    		  SiteSrvc.finishSite(ctrl.site.id).then(function(res){
    			  if(res.status){
    	        	  Msg.showModal('','场地新建成功！').result.then(function(code) {
    	  				if(code == 'ok'){
    	    	    		  $state.go("siteManage");
    	  				}
					  });
    			  }else{
    				  Msg.show('site setup failed!','D',true);
    			  }
    		  },function(error){
  	  			console.dir(error);
    		  });
    	  }else{
    		  Msg.show('please upload or delete pending picture!','W',true);
    		  return;
    	  }
      }
      
      function validateSite(){
    	  if(!ctrl.site){
    		  return false;
    	  }
    	  if(Utils.isTrimEmpty(ctrl.site.name)){
    		  Msg.show('场地名不能为空','W',true);
    		  return false;
    	  }
    	  if(Utils.isNumberEmpty(ctrl.site.flowrate)){
    		  Msg.show('人流量不能为空','W',true);
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
	        	  ctrl.districtList = [{'code':'静安区','desc':'静安区'},{'code':'黄浦区','desc':'黄浦区'}];
	        	  break;
    	  }
      }
    }
})();