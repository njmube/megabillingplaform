(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_payer', Com_payer);

    Com_payer.$inject = ['$resource'];

    function Com_payer ($resource) {
        var resourceUrl =  'api/com-payers/:id';

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
