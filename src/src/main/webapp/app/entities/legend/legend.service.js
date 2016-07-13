(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Legend', Legend);

    Legend.$inject = ['$resource'];

    function Legend ($resource) {
        var resourceUrl =  'api/legends/:id';

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
