(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_concept', Tax_concept);

    Tax_concept.$inject = ['$resource'];

    function Tax_concept ($resource) {
        var resourceUrl =  'api/tax-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {taxpayeraccount: null, tax_type: null, rate: null, concept: null, conceptid: null}},
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
