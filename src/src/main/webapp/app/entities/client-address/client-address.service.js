(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Client_address', Client_address);

    Client_address.$inject = ['$resource'];

    function Client_address ($resource) {
        var resourceUrl =  'api/client-addresses/:id';

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
