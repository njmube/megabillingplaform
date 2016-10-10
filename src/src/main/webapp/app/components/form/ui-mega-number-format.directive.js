(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .directive('uiMegaNumberFormat', uiMegaNumberFormat);

    function uiMegaNumberFormat () {
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            link: linkFunc
        };

        return directive;

        function linkFunc (scope, element, attrs, ngModel) {
			ngModel.$render = function() {
				if(ngModel.$viewValue == null){
					return null;
				}				
				var valString = ngModel.$viewValue.toString();
				var index = valString.indexOf(".");  // Gets the first index where a space occours

                var intPart = valString;
                var decPart = "00";

                if(index != -1){
                    intPart = valString.substr(0, index); // Gets the first part
                    decPart = valString.substr(index + 1);
                }

				var stepOne = intPart.replace(/(\d)(?=(\d{6})+(?!\d))/g, "$1'");
				var stepTwo = stepOne.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");

                element.val(stepTwo + "." + decPart);
            };
        }
    }

})();
