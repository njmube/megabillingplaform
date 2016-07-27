(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .filter('uimeganumberformat', uimeganumberformat);

    function uimeganumberformat() {
        return uimeganumberformatFilter;

        function uimeganumberformatFilter(input) {
            if (input !== null) {
                var valString = input.toString();
				var index = valString.indexOf(".");  // Gets the first index where a space occours

                var intPart = valString;
                var decPart = "00";

                if(index != -1){
                    intPart = valString.substr(0, index); // Gets the first part
                    decPart = valString.substr(index + 1);
                }

				var stepOne = intPart.replace(/(\d)(?=(\d{6})+(?!\d))/g, "$1'");
				var stepTwo = stepOne.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");

                input = stepTwo + "." + decPart;
            }
            return input;
        }
    }
})();
