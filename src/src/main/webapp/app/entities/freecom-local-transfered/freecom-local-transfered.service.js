(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_local_transfered', Freecom_local_transfered);

    Freecom_local_transfered.$inject = ['$resource'];

    function Freecom_local_transfered ($resource) {
        var resourceUrl =  'api/freecom-local-transfereds/:id';

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
