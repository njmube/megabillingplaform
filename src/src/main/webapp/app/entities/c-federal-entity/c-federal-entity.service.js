(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_federal_entity', C_federal_entity);

    C_federal_entity.$inject = ['$resource'];

    function C_federal_entity ($resource) {
        var resourceUrl =  'api/c-federal-entities/:id';

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
