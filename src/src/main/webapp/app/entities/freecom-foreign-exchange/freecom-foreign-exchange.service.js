(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_foreign_exchange', Freecom_foreign_exchange);

    Freecom_foreign_exchange.$inject = ['$resource'];

    function Freecom_foreign_exchange ($resource) {
        var resourceUrl =  'api/freecom-foreign-exchanges/:id';

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
