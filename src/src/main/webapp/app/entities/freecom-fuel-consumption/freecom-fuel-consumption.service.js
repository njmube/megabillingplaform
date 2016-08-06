(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_fuel_consumption', Freecom_fuel_consumption);

    Freecom_fuel_consumption.$inject = ['$resource'];

    function Freecom_fuel_consumption ($resource) {
        var resourceUrl =  'api/freecom-fuel-consumptions/:id';

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
