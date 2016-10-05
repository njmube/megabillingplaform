(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_transactions', Taxpayer_transactions);

    Taxpayer_transactions.$inject = ['$resource'];

    function Taxpayer_transactions ($resource) {
        var resourceUrl =  'api/taxpayer-transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {idaccount: null}},
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
