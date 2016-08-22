(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_commodity', Freecom_commodity);

    Freecom_commodity.$inject = ['$resource'];

    function Freecom_commodity ($resource) {
        var resourceUrl =  'api/freecom-commodities/:id';

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
