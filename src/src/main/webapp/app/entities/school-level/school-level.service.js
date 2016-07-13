(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('School_level', School_level);

    School_level.$inject = ['$resource'];

    function School_level ($resource) {
        var resourceUrl =  'api/school-levels/:id';

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
