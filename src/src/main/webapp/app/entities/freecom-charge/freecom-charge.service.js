(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_charge', Freecom_charge);

    Freecom_charge.$inject = ['$resource'];

    function Freecom_charge ($resource) {
        var resourceUrl =  'api/freecom-charges/:id';

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
