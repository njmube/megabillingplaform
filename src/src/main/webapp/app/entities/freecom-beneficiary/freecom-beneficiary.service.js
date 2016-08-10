(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_beneficiary', Freecom_beneficiary);

    Freecom_beneficiary.$inject = ['$resource'];

    function Freecom_beneficiary ($resource) {
        var resourceUrl =  'api/freecom-beneficiaries/:id';

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
