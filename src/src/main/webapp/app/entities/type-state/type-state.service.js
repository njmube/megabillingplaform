(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Type_state', Type_state);

    Type_state.$inject = ['$resource'];

    function Type_state ($resource) {
        var resourceUrl =  'api/type-states/:id';

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
