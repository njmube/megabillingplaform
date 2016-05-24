(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('File_state', File_state);

    File_state.$inject = ['$resource'];

    function File_state ($resource) {
        var resourceUrl =  'api/file-states/:id';

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
