(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Acquired_title', Acquired_title);

    Acquired_title.$inject = ['$resource'];

    function Acquired_title ($resource) {
        var resourceUrl =  'api/acquired-titles/:id';

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
