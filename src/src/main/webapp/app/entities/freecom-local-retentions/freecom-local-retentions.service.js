(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_local_retentions', Freecom_local_retentions);

    Freecom_local_retentions.$inject = ['$resource'];

    function Freecom_local_retentions ($resource) {
        var resourceUrl =  'api/freecom-local-retentions/:id';

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
