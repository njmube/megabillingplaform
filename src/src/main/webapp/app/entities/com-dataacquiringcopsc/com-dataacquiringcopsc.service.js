(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_dataacquiringcopsc', Com_dataacquiringcopsc);

    Com_dataacquiringcopsc.$inject = ['$resource'];

    function Com_dataacquiringcopsc ($resource) {
        var resourceUrl =  'api/com-dataacquiringcopscs/:id';

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
