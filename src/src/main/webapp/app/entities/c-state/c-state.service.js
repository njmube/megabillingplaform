(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_state', C_state);

    C_state.$inject = ['$resource'];

    function C_state ($resource) {
        var resourceUrl =  'api/c-states/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {countryId: null}},
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
