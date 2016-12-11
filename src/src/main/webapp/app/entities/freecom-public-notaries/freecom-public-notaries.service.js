(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_public_notaries', Freecom_public_notaries);

    Freecom_public_notaries.$inject = ['$resource'];

    function Freecom_public_notaries ($resource) {
        var resourceUrl =  'api/freecom-public-notaries/:id';

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
