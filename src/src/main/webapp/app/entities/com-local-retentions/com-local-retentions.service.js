(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_local_retentions', Com_local_retentions);

    Com_local_retentions.$inject = ['$resource'];

    function Com_local_retentions ($resource) {
        var resourceUrl =  'api/com-local-retentions/:id';

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
