(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_acquiring_data', Com_acquiring_data);

    Com_acquiring_data.$inject = ['$resource'];

    function Com_acquiring_data ($resource) {
        var resourceUrl =  'api/com-acquiring-data/:id';

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
