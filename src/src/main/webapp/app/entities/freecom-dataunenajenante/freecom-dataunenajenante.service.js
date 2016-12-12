(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_dataunenajenante', Freecom_dataunenajenante);

    Freecom_dataunenajenante.$inject = ['$resource'];

    function Freecom_dataunenajenante ($resource) {
        var resourceUrl =  'api/freecom-dataunenajenantes/:id';

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
