(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_kind_payment', Freecom_kind_payment);

    Freecom_kind_payment.$inject = ['$resource'];

    function Freecom_kind_payment ($resource) {
        var resourceUrl =  'api/freecom-kind-payments/:id';

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
