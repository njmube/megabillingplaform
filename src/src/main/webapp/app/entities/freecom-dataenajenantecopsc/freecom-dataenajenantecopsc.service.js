(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_dataenajenantecopsc', Freecom_dataenajenantecopsc);

    Freecom_dataenajenantecopsc.$inject = ['$resource'];

    function Freecom_dataenajenantecopsc ($resource) {
        var resourceUrl =  'api/freecom-dataenajenantecopscs/:id';

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
