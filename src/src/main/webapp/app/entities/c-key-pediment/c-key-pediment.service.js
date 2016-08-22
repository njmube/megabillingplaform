(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_key_pediment', C_key_pediment);

    C_key_pediment.$inject = ['$resource'];

    function C_key_pediment ($resource) {
        var resourceUrl =  'api/c-key-pediments/:id';

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
