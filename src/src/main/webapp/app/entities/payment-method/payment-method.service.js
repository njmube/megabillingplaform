(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Payment_method', Payment_method);

    Payment_method.$inject = ['$resource'];

    function Payment_method ($resource) {
        var resourceUrl =  'api/payment-methods/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null, filtercode:null}},
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
