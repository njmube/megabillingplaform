(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_specific_descriptions', Freecom_specific_descriptions);

    Freecom_specific_descriptions.$inject = ['$resource'];

    function Freecom_specific_descriptions ($resource) {
        var resourceUrl =  'api/freecom-specific-descriptions/:id';

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
