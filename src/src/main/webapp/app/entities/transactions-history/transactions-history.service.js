(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Transactions_history', Transactions_history);

    Transactions_history.$inject = ['$resource', 'DateUtils'];

    function Transactions_history ($resource, DateUtils) {
        var resourceUrl =  'api/transactions-histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_transaction = DateUtils.convertDateTimeFromServer(data.date_transaction);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
