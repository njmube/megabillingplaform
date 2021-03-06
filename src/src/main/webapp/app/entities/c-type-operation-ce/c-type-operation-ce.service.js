(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_type_operation_ce', C_type_operation_ce);

    C_type_operation_ce.$inject = ['$resource'];

    function C_type_operation_ce ($resource) {
        var resourceUrl =  'api/c-type-operation-ces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
