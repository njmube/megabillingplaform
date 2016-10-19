(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_concept', Taxpayer_concept);

    Taxpayer_concept.$inject = ['$resource'];

    function Taxpayer_concept ($resource) {
        var resourceUrl =  'api/taxpayer-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {taxpayeraccount: null, no_identification: null, description: null, measure_unit: null, unit_value: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT'  }
        });
    }
})();
