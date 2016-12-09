(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_public_notaries', Com_public_notaries);

    Com_public_notaries.$inject = ['$resource'];

    function Com_public_notaries ($resource) {
        var resourceUrl =  'api/com-public-notaries/:id';

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
