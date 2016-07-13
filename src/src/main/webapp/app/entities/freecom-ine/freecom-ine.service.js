(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_ine', Freecom_ine);

    Freecom_ine.$inject = ['$resource'];

    function Freecom_ine ($resource) {
        var resourceUrl =  'api/freecom-ines/:id';

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
