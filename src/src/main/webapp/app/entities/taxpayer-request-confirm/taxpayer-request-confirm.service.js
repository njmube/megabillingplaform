(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_request_confirm', Taxpayer_request_confirm);

    Taxpayer_request_confirm.$inject = ['$resource'];

    function Taxpayer_request_confirm ($resource) {
        var resourceUrl =  'api/taxpayer-request-confirms/:id';

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
