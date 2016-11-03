(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_storeroom_paybill', Com_storeroom_paybill);

    Com_storeroom_paybill.$inject = ['$resource'];

    function Com_storeroom_paybill ($resource) {
        var resourceUrl =  'api/com-storeroom-paybills/:id';

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
