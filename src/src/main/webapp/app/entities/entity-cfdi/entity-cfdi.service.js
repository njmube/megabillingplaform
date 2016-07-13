(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Entity_cfdi', Entity_cfdi);

    Entity_cfdi.$inject = ['$resource'];

    function Entity_cfdi ($resource) {
        var resourceUrl =  'api/entity-cfdis/:id';

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
