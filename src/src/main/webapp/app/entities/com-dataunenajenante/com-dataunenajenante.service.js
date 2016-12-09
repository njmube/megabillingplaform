(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_dataunenajenante', Com_dataunenajenante);

    Com_dataunenajenante.$inject = ['$resource'];

    function Com_dataunenajenante ($resource) {
        var resourceUrl =  'api/com-dataunenajenantes/:id';

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
