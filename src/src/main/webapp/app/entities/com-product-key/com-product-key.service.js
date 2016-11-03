(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_product_key', Com_product_key);

    Com_product_key.$inject = ['$resource'];

    function Com_product_key ($resource) {
        var resourceUrl =  'api/com-product-keys/:id';

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
