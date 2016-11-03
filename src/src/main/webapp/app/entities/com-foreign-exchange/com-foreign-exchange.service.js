(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_foreign_exchange', Com_foreign_exchange);

    Com_foreign_exchange.$inject = ['$resource'];

    function Com_foreign_exchange ($resource) {
        var resourceUrl =  'api/com-foreign-exchanges/:id';

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
