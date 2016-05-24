(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_concept', Tax_concept);

    Tax_concept.$inject = ['$resource'];

    function Tax_concept ($resource) {
        var resourceUrl =  'api/tax-concepts/:id';

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
