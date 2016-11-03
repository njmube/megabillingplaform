(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_pfic', Com_pfic);

    Com_pfic.$inject = ['$resource'];

    function Com_pfic ($resource) {
        var resourceUrl =  'api/com-pfics/:id';

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
