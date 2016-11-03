(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_addressee', Com_addressee);

    Com_addressee.$inject = ['$resource'];

    function Com_addressee ($resource) {
        var resourceUrl =  'api/com-addressees/:id';

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
