(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Measure_unit_concept', Measure_unit_concept);

    Measure_unit_concept.$inject = ['$resource'];

    function Measure_unit_concept ($resource) {
        var resourceUrl =  'api/measure-unit-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {taxpayerconcept: null}},
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
