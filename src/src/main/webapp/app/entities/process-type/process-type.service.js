(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Process_type', Process_type);

    Process_type.$inject = ['$resource'];

    function Process_type ($resource) {
        var resourceUrl =  'api/process-types/:id';

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
