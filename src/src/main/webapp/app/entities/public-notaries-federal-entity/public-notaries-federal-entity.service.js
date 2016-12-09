(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Public_notaries_federal_entity', Public_notaries_federal_entity);

    Public_notaries_federal_entity.$inject = ['$resource'];

    function Public_notaries_federal_entity ($resource) {
        var resourceUrl =  'api/public-notaries-federal-entities/:id';

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
