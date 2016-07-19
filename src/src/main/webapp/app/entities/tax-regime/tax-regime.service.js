(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_regime', Tax_regime);

    Tax_regime.$inject = ['$resource'];

    function Tax_regime ($resource) {
        var resourceUrl =  'api/tax-regimes/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
