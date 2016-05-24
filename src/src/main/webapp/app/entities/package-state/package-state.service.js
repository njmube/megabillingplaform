(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Package_state', Package_state);

    Package_state.$inject = ['$resource'];

    function Package_state ($resource) {
        var resourceUrl =  'api/package-states/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
