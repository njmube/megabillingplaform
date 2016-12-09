(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_notary_data', Com_notary_data);

    Com_notary_data.$inject = ['$resource'];

    function Com_notary_data ($resource) {
        var resourceUrl =  'api/com-notary-data/:id';

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
