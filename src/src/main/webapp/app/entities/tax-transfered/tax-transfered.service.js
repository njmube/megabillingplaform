(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_transfered', Tax_transfered);

    Tax_transfered.$inject = ['$resource'];

    function Tax_transfered ($resource) {
        var resourceUrl =  'api/tax-transfereds/:id';

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
