(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_tax_rate', C_tax_rate);

    C_tax_rate.$inject = ['$resource'];

    function C_tax_rate ($resource) {
        var resourceUrl =  'api/c-tax-rates/:id';

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
