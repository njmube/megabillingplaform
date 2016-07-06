(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_colony', C_colony);

    C_colony.$inject = ['$resource'];

    function C_colony ($resource) {
        var resourceUrl =  'api/c-colonies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {municipalityId: null}},
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
