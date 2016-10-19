(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Price_concept', Price_concept);

    Price_concept.$inject = ['$resource'];

    function Price_concept ($resource) {
        var resourceUrl =  'api/price-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params:{taxpayerconcept: null}},
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
