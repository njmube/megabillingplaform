(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_desc_state', Com_desc_state);

    Com_desc_state.$inject = ['$resource'];

    function Com_desc_state ($resource) {
        var resourceUrl =  'api/com-desc-states/:id';

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
