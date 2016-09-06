(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_partial_construction_services', Freecom_partial_construction_services);

    Freecom_partial_construction_services.$inject = ['$resource'];

    function Freecom_partial_construction_services ($resource) {
        var resourceUrl =  'api/freecom-partial-construction-services/:id';

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
