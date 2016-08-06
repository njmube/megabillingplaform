(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_determined', Freecom_determined);

    Freecom_determined.$inject = ['$resource'];

    function Freecom_determined ($resource) {
        var resourceUrl =  'api/freecom-determineds/:id';

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
