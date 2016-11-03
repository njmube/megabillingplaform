(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_taxlegends', Com_taxlegends);

    Com_taxlegends.$inject = ['$resource'];

    function Com_taxlegends ($resource) {
        var resourceUrl =  'api/com-taxlegends/:id';

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
