(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_airline', Com_airline);

    Com_airline.$inject = ['$resource'];

    function Com_airline ($resource) {
        var resourceUrl =  'api/com-airlines/:id';

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
