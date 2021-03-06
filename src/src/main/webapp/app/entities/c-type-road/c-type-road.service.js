(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_type_road', C_type_road);

    C_type_road.$inject = ['$resource'];

    function C_type_road ($resource) {
        var resourceUrl =  'api/c-type-roads/:id';

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
