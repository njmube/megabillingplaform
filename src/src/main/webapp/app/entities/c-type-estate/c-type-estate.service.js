(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_type_estate', C_type_estate);

    C_type_estate.$inject = ['$resource'];

    function C_type_estate ($resource) {
        var resourceUrl =  'api/c-type-estates/:id';

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
