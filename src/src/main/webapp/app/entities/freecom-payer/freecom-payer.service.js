(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_payer', Freecom_payer);

    Freecom_payer.$inject = ['$resource'];

    function Freecom_payer ($resource) {
        var resourceUrl =  'api/freecom-payers/:id';

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
