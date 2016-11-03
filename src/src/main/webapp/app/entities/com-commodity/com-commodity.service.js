(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_commodity', Com_commodity);

    Com_commodity.$inject = ['$resource'];

    function Com_commodity ($resource) {
        var resourceUrl =  'api/com-commodities/:id';

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
