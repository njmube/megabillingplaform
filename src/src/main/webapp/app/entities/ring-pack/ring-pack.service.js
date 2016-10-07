(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Ring_pack', Ring_pack);

    Ring_pack.$inject = ['$resource'];

    function Ring_pack ($resource) {
        var resourceUrl =  'api/ring-packs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'buytransactions': { method: 'GET', params: {idaccount: null, idring_pack: null}},
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
