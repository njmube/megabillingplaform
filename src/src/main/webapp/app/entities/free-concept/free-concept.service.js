(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_concept', Free_concept);

    Free_concept.$inject = ['$resource'];

    function Free_concept ($resource) {
        var resourceUrl =  'api/free-concepts/:id';

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
