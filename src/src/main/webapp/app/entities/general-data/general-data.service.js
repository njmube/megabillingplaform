(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('General_data', General_data);

    General_data.$inject = ['$resource'];

    function General_data ($resource) {
        var resourceUrl =  'api/general-data/:id';

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
