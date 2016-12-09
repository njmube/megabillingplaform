(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_dataenajenantecopsc', Com_dataenajenantecopsc);

    Com_dataenajenantecopsc.$inject = ['$resource'];

    function Com_dataenajenantecopsc ($resource) {
        var resourceUrl =  'api/com-dataenajenantecopscs/:id';

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
