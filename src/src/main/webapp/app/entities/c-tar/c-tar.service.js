(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_tar', C_tar);

    C_tar.$inject = ['$resource'];

    function C_tar ($resource) {
        var resourceUrl =  'api/c-tars/:id';

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
