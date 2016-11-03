(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_ine_entity', Com_ine_entity);

    Com_ine_entity.$inject = ['$resource'];

    function Com_ine_entity ($resource) {
        var resourceUrl =  'api/com-ine-entities/:id';

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
