(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_account', Taxpayer_account);

    Taxpayer_account.$inject = ['$resource'];

    function Taxpayer_account ($resource) {
        var resourceUrl =  'api/taxpayer-accounts/:id';

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
