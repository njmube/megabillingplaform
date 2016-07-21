(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_acquired_title', C_acquired_title);

    C_acquired_title.$inject = ['$resource'];

    function C_acquired_title ($resource) {
        var resourceUrl =  'api/c-acquired-titles/:id';

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
