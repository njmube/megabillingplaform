(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_local_taxes', Freecom_local_taxes);

    Freecom_local_taxes.$inject = ['$resource'];

    function Freecom_local_taxes ($resource) {
        var resourceUrl =  'api/freecom-local-taxes/:id';

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
