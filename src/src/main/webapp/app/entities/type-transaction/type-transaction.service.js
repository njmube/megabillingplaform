(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Type_transaction', Type_transaction);

    Type_transaction.$inject = ['$resource'];

    function Type_transaction ($resource) {
        var resourceUrl =  'api/type-transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
