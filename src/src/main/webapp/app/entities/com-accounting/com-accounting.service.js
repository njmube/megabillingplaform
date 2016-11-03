(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_accounting', Com_accounting);

    Com_accounting.$inject = ['$resource'];

    function Com_accounting ($resource) {
        var resourceUrl =  'api/com-accountings/:id';

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
