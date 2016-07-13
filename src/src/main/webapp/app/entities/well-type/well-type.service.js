(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Well_type', Well_type);

    Well_type.$inject = ['$resource'];

    function Well_type ($resource) {
        var resourceUrl =  'api/well-types/:id';

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
