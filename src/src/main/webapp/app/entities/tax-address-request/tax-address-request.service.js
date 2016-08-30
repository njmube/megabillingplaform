(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_address_request', Tax_address_request);

    Tax_address_request.$inject = ['$resource'];

    function Tax_address_request ($resource) {
        var resourceUrl =  'api/tax-address-requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
