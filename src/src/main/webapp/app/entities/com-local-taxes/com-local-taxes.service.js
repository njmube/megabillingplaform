(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_local_taxes', Com_local_taxes);

    Com_local_taxes.$inject = ['$resource'];

    function Com_local_taxes ($resource) {
        var resourceUrl =  'api/com-local-taxes/:id';

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