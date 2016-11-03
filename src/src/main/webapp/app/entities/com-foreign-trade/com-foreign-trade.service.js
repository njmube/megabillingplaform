(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_foreign_trade', Com_foreign_trade);

    Com_foreign_trade.$inject = ['$resource'];

    function Com_foreign_trade ($resource) {
        var resourceUrl =  'api/com-foreign-trades/:id';

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
