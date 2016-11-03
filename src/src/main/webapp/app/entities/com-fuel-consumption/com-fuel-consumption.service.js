(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_fuel_consumption', Com_fuel_consumption);

    Com_fuel_consumption.$inject = ['$resource'];

    function Com_fuel_consumption ($resource) {
        var resourceUrl =  'api/com-fuel-consumptions/:id';

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
