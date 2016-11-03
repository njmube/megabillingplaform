(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_ecc_11_transfer', Com_ecc_11_transfer);

    Com_ecc_11_transfer.$inject = ['$resource'];

    function Com_ecc_11_transfer ($resource) {
        var resourceUrl =  'api/com-ecc-11-transfers/:id';

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
