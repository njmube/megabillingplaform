(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_destruction_certificate', Com_destruction_certificate);

    Com_destruction_certificate.$inject = ['$resource'];

    function Com_destruction_certificate ($resource) {
        var resourceUrl =  'api/com-destruction-certificates/:id';

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
