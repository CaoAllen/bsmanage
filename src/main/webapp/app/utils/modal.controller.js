(function() {

	angular.module('manageApp.utils').controller('ModalCtrl', ModalCtrl);

	ModalCtrl.$inject = [
		'$uibModal',
		'$uibModalInstance', 
		'message',
		'$translate',
	];

	function ModalCtrl($uibModal, $uibModalInstance, message, $translate) {
		var ctrl = this;
		ctrl.closeMark = '×';
		ctrl.content = message.content;
		ctrl.title = !message.title ? getDefaultTitle(message.type) : message.title;
		ctrl.closeable = !!message.closeable;
		console.dir(message);
		if(message.buttons && message.buttons.length > 0){
			ctrl.buttons = message.buttons;
		}else if(message.type === 'confirm'){
			ctrl.buttons = [{'text':'确定','code':'ok'},{'text':'取消','code':'cancel'}];
		}else {
			ctrl.buttons = [{'text':'确定','code':'ok'}];
		}
		ctrl.titleClass = getClass(message.type);
		
		ctrl.click = click;
		ctrl.close = close;

			
		function click(button) {
			$uibModalInstance.close(button);		
		};
		
		function close() {
			$uibModalInstance.dismiss(button);		
		};
		
		function getClass(type){
			if(!type) type = 'info';
			switch(type){
				case 'confirm': return "fa-check-circle text-info";
				case 'warning': return "fa-warning text-warning";
				case 'danger': return "fa-times-circle text-danger";
				default : return "fa-info-circle text-info";
			}
		}
		
		function getDefaultTitle(type){
			if(!type) type = 'info';
			switch(type){
				case 'confirm': return "Modal.Confirmation";
				case 'warning': return "Modal.Warning";
				case 'danger': return "Modal.Danger";
				default : return "Modal.Notification";
			}
			
		}
	}
	
})();
