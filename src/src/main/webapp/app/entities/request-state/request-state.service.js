(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Request_state', Request_state);

    Request_state.$inject = ['$resource'];

    function Request_state ($resource) {
        var resourceUrl =  'api/request-states/:id';

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
