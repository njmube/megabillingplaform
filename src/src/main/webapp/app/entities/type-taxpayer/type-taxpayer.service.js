(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Type_taxpayer', Type_taxpayer);

    Type_taxpayer.$inject = ['$resource'];

    function Type_taxpayer ($resource) {
        var resourceUrl =  'api/type-taxpayers/:id';

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
