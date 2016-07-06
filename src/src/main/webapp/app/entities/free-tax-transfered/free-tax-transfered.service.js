(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_tax_transfered', Free_tax_transfered);

    Free_tax_transfered.$inject = ['$resource'];

    function Free_tax_transfered ($resource) {
        var resourceUrl =  'api/free-tax-transfereds/:id';

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
