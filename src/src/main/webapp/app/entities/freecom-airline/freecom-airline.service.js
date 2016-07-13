(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_airline', Freecom_airline);

    Freecom_airline.$inject = ['$resource'];

    function Freecom_airline ($resource) {
        var resourceUrl =  'api/freecom-airlines/:id';

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
