(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_type_series', C_type_series);

    C_type_series.$inject = ['$resource'];

    function C_type_series ($resource) {
        var resourceUrl =  'api/c-type-series/:id';

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
