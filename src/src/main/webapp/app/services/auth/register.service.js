(function () {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {
            'sugesuser': { method: 'GET', params: {name_f_s: null}, isArray: false}
        });
    }
})();
