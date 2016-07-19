(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_country', C_country);

    C_country.$inject = ['$resource'];

    function C_country ($resource) {
        var resourceUrl =  'api/c-countries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET',
                isArray: true,
                params: {pg: null,filtername: null}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
