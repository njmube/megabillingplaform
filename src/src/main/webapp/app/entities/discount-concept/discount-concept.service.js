(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Discount_concept', Discount_concept);

    Discount_concept.$inject = ['$resource'];

    function Discount_concept ($resource) {
        var resourceUrl =  'api/discount-concepts/:id';

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
