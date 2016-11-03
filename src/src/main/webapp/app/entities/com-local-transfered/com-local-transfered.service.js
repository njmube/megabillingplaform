(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_local_transfered', Com_local_transfered);

    Com_local_transfered.$inject = ['$resource'];

    function Com_local_transfered ($resource) {
        var resourceUrl =  'api/com-local-transfereds/:id';

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
