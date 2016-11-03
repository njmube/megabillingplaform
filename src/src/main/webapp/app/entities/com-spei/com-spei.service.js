(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_spei', Com_spei);

    Com_spei.$inject = ['$resource'];

    function Com_spei ($resource) {
        var resourceUrl =  'api/com-speis/:id';

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
