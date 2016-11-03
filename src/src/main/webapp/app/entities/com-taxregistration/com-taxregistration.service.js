(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_taxregistration', Com_taxregistration);

    Com_taxregistration.$inject = ['$resource'];

    function Com_taxregistration ($resource) {
        var resourceUrl =  'api/com-taxregistrations/:id';

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
