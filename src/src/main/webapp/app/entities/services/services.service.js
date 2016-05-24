(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Services', Services);

    Services.$inject = ['$resource'];

    function Services ($resource) {
        var resourceUrl =  'api/services/:id';

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
