(function() {
	var as = angular.module('manageApp.utils');
	as.factory('Msg', function($rootScope, $filter, $window, $uibModal) {
		var service = {
				show: show,
				showModal: showModal,
				clear: clear
		};

		return service;
        
		function show(text, type, isShow){
			//type: success, warning, danger, info;
			switch(type){
				case 's':
				case 'S': type = 'success'; break;
				case 'w':
				case 'W': type = 'warning'; break;
				case 'd':
				case 'D': type = 'danger'; break;
				default : type = 'info';
			}
			$rootScope.setMessage({text:text, type:type, show: !!isShow});
			$window.scrollTo(0,0);
		}
		
		function showModal(title,content,type,closeable,draggabled,buttons){
			var message = {content:content, title:title, type:type, closeable:closeable,buttons:buttons};
			var model = {
				backdrop : "static",
				keyboard : false,
				templateUrl : 'app/utils/modal.html',
				controller : 'ModalCtrl',
				controllerAs : 'ctrl',
				backdrop:'static',
                size : 'sm',
				resolve: {
	     			message: function () {
	    				return message;
	    			}
	   			}
			};
			if(draggabled){
				return draggable($uibModal.open(model));
			}
			return $uibModal.open(model);
		}
		
		function clear(){
			$rootScope.clearMessage();
		}
		
		function draggable(modal) {
			modal.rendered.then(function() {
				$('.modal-dialog').draggable({
    				handle: ".modal-header"
    			});
			});
			return modal;
		}
	});
})();
