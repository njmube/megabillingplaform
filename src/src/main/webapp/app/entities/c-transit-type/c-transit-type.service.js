(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_transit_type', C_transit_type);

    C_transit_type.$inject = ['$resource'];

    function C_transit_type ($resource) {
        var resourceUrl =  'api/c-transit-types/:id';

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
