(function() {
	angular.module('manageApp.utils').factory('Utils', function($filter) {
		var service = {
				isTrimEmpty: isTrimEmpty,
				isStringEmpty: isStringEmpty,
				isNumberEmpty: isNumberEmpty,
				noLessThanZero: noLessThanZero,
				isGreaterThanZero: isGreaterThanZero,
				translate: translate,
				translateMsg: translateMsg,
				reFormatDate: reFormatDate
				
		};

		return service;

		function translate(key){
			return $filter('translate')(key);
		}
		
		function translateMsg(key){
			return translate('MESSAGE.' + key);
		}
		
		function isTrimEmpty(str){
			return isStringEmpty(str) ? true : isStringEmpty(str.trim());
		}
		
		function isStringEmpty(str){
			return str === undefined || str === '' || str === null;
		}
		
		function isNumberEmpty(num){
			return isStringEmpty(num) || Number(num) === 0;
		}
		//contain 0
		function noLessThanZero(num){
			if(isStringEmpty(num)) return false;
			return Number(num) >= 0;
		}
		
		function isGreaterThanZero(num){
			if(isNumberEmpty(num)) return false;
			return Number(num) > 0 ;
		}
		
		function reFormatDate(dateStr){
			if(!isStringEmpty(dateStr) && dateStr.split(":").length > 2){
				var newDate = new Date();
				var hours = dateStr.split(":")[0];
				var minutes = dateStr.split(":")[1];
				newDate.setHours(hours);
				newDate.setMinutes(minutes);
				return newDate;
			}
			return null;
		}
	});
})();