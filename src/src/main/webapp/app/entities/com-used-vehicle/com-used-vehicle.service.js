(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_used_vehicle', Com_used_vehicle);

    Com_used_vehicle.$inject = ['$resource'];

    function Com_used_vehicle ($resource) {
        var resourceUrl =  'api/com-used-vehicles/:id';

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
