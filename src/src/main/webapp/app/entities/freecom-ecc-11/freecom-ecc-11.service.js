(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_ecc11', Freecom_ecc11);

    Freecom_ecc11.$inject = ['$resource'];

    function Freecom_ecc11 ($resource) {
        var resourceUrl =  'api/freecom-ecc-11-s/:id';

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
