(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Way_payment', Way_payment);

    Way_payment.$inject = ['$resource'];

    function Way_payment ($resource) {
        var resourceUrl =  'api/way-payments/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
