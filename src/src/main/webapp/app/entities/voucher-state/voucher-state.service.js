(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Voucher_state', Voucher_state);

    Voucher_state.$inject = ['$resource'];

    function Voucher_state ($resource) {
        var resourceUrl =  'api/voucher-states/:id';

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
