(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .filter('uimegahtmltoplaintext', uimegahtmltoplaintext);

    function uimegahtmltoplaintext() {
        return uimegahtmltoplaintextFilter;

        function uimegahtmltoplaintextFilter(input) {
            return  input ? String(input).replace(/<[^>]+>/gm, '') : '';
        }
    }
})();
