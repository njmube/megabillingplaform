(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_request', Taxpayer_request);

    Taxpayer_request.$inject = ['$resource'];

    function Taxpayer_request ($resource) {
        var resourceUrl =  'api/taxpayer-requests/:id';

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
