(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Billing_account_state', Billing_account_state);

    Billing_account_state.$inject = ['$resource'];

    function Billing_account_state ($resource) {
        var resourceUrl =  'api/billing-account-states/:id';

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
