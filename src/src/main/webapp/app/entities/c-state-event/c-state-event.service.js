(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_state_event', C_state_event);

    C_state_event.$inject = ['$resource'];

    function C_state_event ($resource) {
        var resourceUrl =  'api/c-state-events/:id';

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
