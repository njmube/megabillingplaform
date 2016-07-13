(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Committee', Committee);

    Committee.$inject = ['$resource'];

    function Committee ($resource) {
        var resourceUrl =  'api/committees/:id';

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
