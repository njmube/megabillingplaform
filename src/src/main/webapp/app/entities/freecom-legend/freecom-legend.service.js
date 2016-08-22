(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_legend', Freecom_legend);

    Freecom_legend.$inject = ['$resource'];

    function Freecom_legend ($resource) {
        var resourceUrl =  'api/freecom-legends/:id';

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
