(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Category_product', Category_product);

    Category_product.$inject = ['$resource'];

    function Category_product ($resource) {
        var resourceUrl =  'api/category-products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
