(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_ecc11_transfer', Freecom_ecc11_transfer);

    Freecom_ecc11_transfer.$inject = ['$resource'];

    function Freecom_ecc11_transfer ($resource) {
        var resourceUrl =  'api/freecom-ecc-11-transfers/:id';

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
