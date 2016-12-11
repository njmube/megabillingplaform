(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_desc_estate', Com_desc_estate);

    Com_desc_estate.$inject = ['$resource'];

    function Com_desc_estate ($resource) {
        var resourceUrl =  'api/com-desc-estates/:id';

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
