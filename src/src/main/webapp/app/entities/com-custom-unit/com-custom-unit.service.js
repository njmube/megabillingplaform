(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_custom_unit', Com_custom_unit);

    Com_custom_unit.$inject = ['$resource'];

    function Com_custom_unit ($resource) {
        var resourceUrl =  'api/com-custom-units/:id';

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
