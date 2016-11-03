(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_kind_payment', Com_kind_payment);

    Com_kind_payment.$inject = ['$resource'];

    function Com_kind_payment ($resource) {
        var resourceUrl =  'api/com-kind-payments/:id';

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
