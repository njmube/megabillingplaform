(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_data_enajenante', Freecom_data_enajenante);

    Freecom_data_enajenante.$inject = ['$resource'];

    function Freecom_data_enajenante ($resource) {
        var resourceUrl =  'api/freecom-data-enajenantes/:id';

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
