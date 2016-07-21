(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_well_type', C_well_type);

    C_well_type.$inject = ['$resource'];

    function C_well_type ($resource) {
        var resourceUrl =  'api/c-well-types/:id';

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
