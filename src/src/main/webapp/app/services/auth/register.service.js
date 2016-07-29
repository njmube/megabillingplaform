(function () {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {
            'query': { method: 'GET', isArray: true, params: {login: null}}
        });
    }
})();
