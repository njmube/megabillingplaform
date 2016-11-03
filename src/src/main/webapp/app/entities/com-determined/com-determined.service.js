(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_determined', Com_determined);

    Com_determined.$inject = ['$resource'];

    function Com_determined ($resource) {
        var resourceUrl =  'api/com-determineds/:id';

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
