(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Key_entity', Key_entity);

    Key_entity.$inject = ['$resource'];

    function Key_entity ($resource) {
        var resourceUrl =  'api/key-entities/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {pg: null}
            },
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
