(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_dataacquiringcopsc', Freecom_dataacquiringcopsc);

    Freecom_dataacquiringcopsc.$inject = ['$resource'];

    function Freecom_dataacquiringcopsc ($resource) {
        var resourceUrl =  'api/freecom-dataacquiringcopscs/:id';

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
