(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Voucher_type', Voucher_type);

    Voucher_type.$inject = ['$resource'];

    function Voucher_type ($resource) {
        var resourceUrl =  'api/voucher-types/:id';

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
