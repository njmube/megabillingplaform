(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_partial_construction_services', Com_partial_construction_services);

    Com_partial_construction_services.$inject = ['$resource'];

    function Com_partial_construction_services ($resource) {
        var resourceUrl =  'api/com-partial-construction-services/:id';

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
