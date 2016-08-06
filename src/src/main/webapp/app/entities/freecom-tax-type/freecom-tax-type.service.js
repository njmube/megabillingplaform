(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_tax_type', Freecom_tax_type);

    Freecom_tax_type.$inject = ['$resource'];

    function Freecom_tax_type ($resource) {
        var resourceUrl =  'api/freecom-tax-types/:id';

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
