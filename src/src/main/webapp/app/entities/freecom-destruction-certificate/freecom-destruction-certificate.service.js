(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_destruction_certificate', Freecom_destruction_certificate);

    Freecom_destruction_certificate.$inject = ['$resource'];

    function Freecom_destruction_certificate ($resource) {
        var resourceUrl =  'api/freecom-destruction-certificates/:id';

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
