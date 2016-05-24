(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_zip_code', C_zip_code);

    C_zip_code.$inject = ['$resource'];

    function C_zip_code ($resource) {
        var resourceUrl =  'api/c-zip-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
