(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Scope', Scope);

    Scope.$inject = ['$resource'];

    function Scope ($resource) {
        var resourceUrl =  'api/scopes/:id';

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
