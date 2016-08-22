(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_foreign_trade', Freecom_foreign_trade);

    Freecom_foreign_trade.$inject = ['$resource'];

    function Freecom_foreign_trade ($resource) {
        var resourceUrl =  'api/freecom-foreign-trades/:id';

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
