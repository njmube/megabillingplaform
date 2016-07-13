(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_pfic', Freecom_pfic);

    Freecom_pfic.$inject = ['$resource'];

    function Freecom_pfic ($resource) {
        var resourceUrl =  'api/freecom-pfics/:id';

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
