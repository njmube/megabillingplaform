(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_address', Tax_address);

    Tax_address.$inject = ['$resource'];

    function Tax_address ($resource) {
        var resourceUrl =  'api/tax-addresses/:id';

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
