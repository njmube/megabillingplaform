(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_location', C_location);

    C_location.$inject = ['$resource'];

    function C_location ($resource) {
        var resourceUrl =  'api/c-locations/:id';

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
