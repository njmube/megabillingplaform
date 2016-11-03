(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_ecc_11', Com_ecc_11);

    Com_ecc_11.$inject = ['$resource'];

    function Com_ecc_11 ($resource) {
        var resourceUrl =  'api/com-ecc-11-s/:id';

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
