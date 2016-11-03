(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_accreditation_ieps', Com_accreditation_ieps);

    Com_accreditation_ieps.$inject = ['$resource'];

    function Com_accreditation_ieps ($resource) {
        var resourceUrl =  'api/com-accreditation-ieps/:id';

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
