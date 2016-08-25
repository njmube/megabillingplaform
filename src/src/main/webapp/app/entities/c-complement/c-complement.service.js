(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_complement', C_complement);

    C_complement.$inject = ['$resource'];

    function C_complement ($resource) {
        var resourceUrl =  'api/c-complements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET',
                isArray: true,
                params: {pg: null,filtername: null}
            },
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
