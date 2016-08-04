(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_storeroom_paybill', Freecom_storeroom_paybill);

    Freecom_storeroom_paybill.$inject = ['$resource'];

    function Freecom_storeroom_paybill ($resource) {
        var resourceUrl =  'api/freecom-storeroom-paybills/:id';

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
