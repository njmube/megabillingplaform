(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_ine_entity', Freecom_ine_entity);

    Freecom_ine_entity.$inject = ['$resource'];

    function Freecom_ine_entity ($resource) {
        var resourceUrl =  'api/freecom-ine-entities/:id';

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
