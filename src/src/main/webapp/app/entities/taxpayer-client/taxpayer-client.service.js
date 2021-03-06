(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_client', Taxpayer_client);

    Taxpayer_client.$inject = ['$resource'];

    function Taxpayer_client ($resource) {
        var resourceUrl =  'api/taxpayer-clients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {taxpayeraccount: null, rfc: null, bussinesname: null, email: null, phone: null} },
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
