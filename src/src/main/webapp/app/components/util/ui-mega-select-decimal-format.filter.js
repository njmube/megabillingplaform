(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .filter('uimegaselectdecimalformat', uimegaselectdecimalformat);

    function uimegaselectdecimalformat() {
        return uimegaselectdecimalformatFilter;

        function uimegaselectdecimalformatFilter(input) {
            if (!isNaN(input)) {
				input = input.toFixed(2);
            }
            return input;
        }
    }
})();
