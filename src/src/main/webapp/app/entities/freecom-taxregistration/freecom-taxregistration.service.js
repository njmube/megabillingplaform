(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_taxregistration', Freecom_taxregistration);

    Freecom_taxregistration.$inject = ['$resource'];

    function Freecom_taxregistration ($resource) {
        var resourceUrl =  'api/freecom-taxregistrations/:id';

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
