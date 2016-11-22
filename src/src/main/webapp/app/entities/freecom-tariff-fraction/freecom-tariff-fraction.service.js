(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_tariff_fraction', Freecom_tariff_fraction);

    Freecom_tariff_fraction.$inject = ['$resource'];

    function Freecom_tariff_fraction ($resource) {
        var resourceUrl =  'api/freecom-tariff-fractions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {pg: null}},
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
