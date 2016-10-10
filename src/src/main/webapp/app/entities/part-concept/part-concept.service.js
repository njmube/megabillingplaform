(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Part_concept', Part_concept);

    Part_concept.$inject = ['$resource'];

    function Part_concept ($resource) {
        var resourceUrl =  'api/part-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {conceptid: null}},
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
