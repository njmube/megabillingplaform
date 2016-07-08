(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Tax_types', Tax_types);

    Tax_types.$inject = ['$resource'];

    function Tax_types ($resource) {
        var resourceUrl =  'api/tax-types/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
