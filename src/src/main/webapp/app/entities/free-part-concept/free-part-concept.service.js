(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_part_concept', Free_part_concept);

    Free_part_concept.$inject = ['$resource'];

    function Free_part_concept ($resource) {
        var resourceUrl =  'api/free-part-concepts/:id';

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
