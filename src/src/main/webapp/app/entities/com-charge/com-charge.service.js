(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_charge', Com_charge);

    Com_charge.$inject = ['$resource'];

    function Com_charge ($resource) {
        var resourceUrl =  'api/com-charges/:id';

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
