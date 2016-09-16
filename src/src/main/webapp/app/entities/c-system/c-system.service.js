(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_system', C_system);

    C_system.$inject = ['$resource'];

    function C_system ($resource) {
        var resourceUrl =  'api/c-systems/:id';

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
