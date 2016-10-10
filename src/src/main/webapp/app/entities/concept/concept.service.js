(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Concept', Concept);

    Concept.$inject = ['$resource'];

    function Concept ($resource) {
        var resourceUrl =  'api/concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, param: {taxpayeraccount: null, no_identification: null, description: null, measure_unit: null, unit_value: null}},
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
