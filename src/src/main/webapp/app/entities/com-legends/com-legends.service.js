(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_legends', Com_legends);

    Com_legends.$inject = ['$resource'];

    function Com_legends ($resource) {
        var resourceUrl =  'api/com-legends/:id';

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
