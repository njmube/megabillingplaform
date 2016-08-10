(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_product_key', Freecom_product_key);

    Freecom_product_key.$inject = ['$resource'];

    function Freecom_product_key ($resource) {
        var resourceUrl =  'api/freecom-product-keys/:id';

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
