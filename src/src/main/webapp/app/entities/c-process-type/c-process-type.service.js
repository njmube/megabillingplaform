(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_process_type', C_process_type);

    C_process_type.$inject = ['$resource'];

    function C_process_type ($resource) {
        var resourceUrl =  'api/c-process-types/:id';

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
