(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Rate_type', Rate_type);

    Rate_type.$inject = ['$resource'];

    function Rate_type ($resource) {
        var resourceUrl =  'api/rate-types/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                isArray: true,
                params: {filtername: null}},
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
