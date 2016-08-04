(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_used_vehicle', Freecom_used_vehicle);

    Freecom_used_vehicle.$inject = ['$resource'];

    function Freecom_used_vehicle ($resource) {
        var resourceUrl =  'api/freecom-used-vehicles/:id';

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
