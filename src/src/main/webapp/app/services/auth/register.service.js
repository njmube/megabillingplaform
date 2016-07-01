(function () {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
