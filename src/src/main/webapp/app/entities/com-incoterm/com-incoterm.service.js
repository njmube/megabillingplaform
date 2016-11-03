(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_incoterm', Com_incoterm);

    Com_incoterm.$inject = ['$resource'];

    function Com_incoterm ($resource) {
        var resourceUrl =  'api/com-incoterms/:id';

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
