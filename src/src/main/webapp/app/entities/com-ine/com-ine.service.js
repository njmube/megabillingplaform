(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_ine', Com_ine);

    Com_ine.$inject = ['$resource'];

    function Com_ine ($resource) {
        var resourceUrl =  'api/com-ines/:id';

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
