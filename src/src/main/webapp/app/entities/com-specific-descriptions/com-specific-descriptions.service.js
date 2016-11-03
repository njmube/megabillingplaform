(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_specific_descriptions', Com_specific_descriptions);

    Com_specific_descriptions.$inject = ['$resource'];

    function Com_specific_descriptions ($resource) {
        var resourceUrl =  'api/com-specific-descriptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
