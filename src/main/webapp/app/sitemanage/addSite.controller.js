(function(){
	var as = angular.module('manageApp.site');
    as.controller('AddSiteController', AddSiteCtrl);
    AddSiteCtrl.$inject = ['$scope','$state','$rootScope','$translate','fileReader','FileUploader','SiteSrvc','ConfigSrvc'];
    function AddSiteCtrl($scope, $state, $rootScope, $translate, fileReader, FileUploader, SiteSrvc, ConfigSrvc) {
      var ctrl = this;
      ctrl.stage = 1;
      ctrl.startDate = new Date();
      ctrl.cancel = cancel;
      ctrl.next = next;
      ctrl.back = back;
      ctrl.finish = finish;
      ctrl.addPrice = addPrice;
      ctrl.removePrice = removePrice;
      
      ctrl.prices = [];
      addPrice();
      
      ctrl.images = [];
      ctrl.uploadImg = uploadImg;
      ctrl.deleteImg = deleteImg;
      
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
      
      function initUploader(){
    	  ctrl.uploader = new FileUploader({
        	  url: ConfigSrvc.getBaseUrl()+'/site/picture/uplad',
        	  formData:[]
          });
    	  ctrl.uploader.onSuccessItem = function(fileItem, response, status,  headers) {             
        	  console.dir(fileItem);              
        	  console.dir(response);              
        	  console.dir(status);              
        	  console.dir(headers);   
          };
          ctrl.uploader.onAfterAddingFile = function(item){
        	  console.log("onAfterAddingFile");
        	  console.dir(item);   
          }
          ctrl.uploader.onWhenAddingFileFailed  = function(item, filter, options){
        	  console.log("onWhenAddingFileFailed");
        	  console.dir(item);   
          }
          ctrl.uploader. onBeforeUploadItem = function(item){
        	  console.log("onBeforeUploadItem");
        	  console.dir(item);   
          }
      }
      
      function cancel(){
    	  $state.go("siteManage");
      }
      
      function next(){
    	  ctrl.stage ++;
    	  return;
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
        	  				$rootScope.setMessage({text:'add success!', type:'success', show:true});
        	  				ctrl.stage ++;
        	  			}
        	  		},function(error){
        	  			console.dir(error);
        	  		});
    	  		}else{
    	  			SiteSrvc.updateSite(ctrl.site).then(function(res){
        	  			if(res.data){
        	  				ctrl.stage ++;
        	  				$rootScope.setMessage({text:'update success!', type:'success', show:true});
        	  			}
        	  		},function(error){
        	  			console.dir(error);
        	  		});
    	  		}
    	  		break;
    	  	};
    	  	//add community
    	  	case 2: {
	  			SiteSrvc.addCommunity(ctrl.site.community).then(function(res){
    	  			if(res.siteId > 0){
    	  				ctrl.site.id = res.siteId;
    	  				$rootScope.setMessage({text:'add success!', type:'success', show:true});
    	  				ctrl.stage ++;
    	  			}
    	  		},function(error){
    	  			console.dir(error);
    	  		});
    	  	};
    	  	//add price
    	  	case 3: {
    	  		ctrl.stage ++;
    	  	};
    	  }
      }
      
      function finish(){
    	  var continued = true;
    	  angular.forEach(ctrl.images,function(item,index){
    		  if(continued && item.isUploaded){
    			  continued = false;
    		  }
    	  });
    	  if(continued){
    		  $rootScope.setMessage({text:'site setup success!', type:'success', show:true});
    		  $state.go("siteManage");
    	  }else{
    		  $rootScope.setMessage({text:'please upload or delete pending picture!', type:'warning', show:true});
    		  return;
    	  }
      }
      
      function validateSite(){
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
    			  $rootScope.setMessage({text:'Upload succesfully!', type:'success', show:true});
    	    	  ctrl.images[i].uploadBtn = 'site.picture.uploaded';
    	    	  ctrl.images[i].pictureId = res.data.picture.pictureId;
    		  }else {
    			  $rootScope.setMessage({text:'Upload failed!', type:'danger', show:true});
    	    	  ctrl.images[i].uploadBtn = 'site.picture.upload';
    		  }
    	  },function(error){
    		  console.dir(error);
			  $rootScope.setMessage({text:'Upload failed!', type:'danger', show:true});
	    	  ctrl.images[i].uploadBtn = 'site.picture.upload';
    	  });
      }

      function deleteImg(i){
    	  ctrl.images.splice(i, 1);
    	  SiteSrvc.deletePicture(ctrl.images[i].pictureId).then(function(res){
    		  if(res.data && res.data.status){
    			  $rootScope.setMessage({text:'delete succesfully!', type:'success', show:true});
    		  }
    	  });
      }
    }
})();