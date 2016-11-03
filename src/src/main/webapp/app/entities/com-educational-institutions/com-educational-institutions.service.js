(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_educational_institutions', Com_educational_institutions);

    Com_educational_institutions.$inject = ['$resource'];

    function Com_educational_institutions ($resource) {
        var resourceUrl =  'api/com-educational-institutions/:id';

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
