(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_type_operation', C_type_operation);

    C_type_operation.$inject = ['$resource'];

    function C_type_operation ($resource) {
        var resourceUrl =  'api/c-type-operations/:id';

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
