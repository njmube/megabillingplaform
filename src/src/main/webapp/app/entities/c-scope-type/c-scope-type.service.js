(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_scope_type', C_scope_type);

    C_scope_type.$inject = ['$resource'];

    function C_scope_type ($resource) {
        var resourceUrl =  'api/c-scope-types/:id';

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
