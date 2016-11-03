(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_tariff_fraction', Com_tariff_fraction);

    Com_tariff_fraction.$inject = ['$resource'];

    function Com_tariff_fraction ($resource) {
        var resourceUrl =  'api/com-tariff-fractions/:id';

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
