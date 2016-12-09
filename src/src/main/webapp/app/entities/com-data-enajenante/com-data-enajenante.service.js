(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_data_enajenante', Com_data_enajenante);

    Com_data_enajenante.$inject = ['$resource'];

    function Com_data_enajenante ($resource) {
        var resourceUrl =  'api/com-data-enajenantes/:id';

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
