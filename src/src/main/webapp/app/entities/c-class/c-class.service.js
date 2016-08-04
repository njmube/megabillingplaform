(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_class', C_class);

    C_class.$inject = ['$resource'];

    function C_class ($resource) {
        var resourceUrl =  'api/c-classes/:id';

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
