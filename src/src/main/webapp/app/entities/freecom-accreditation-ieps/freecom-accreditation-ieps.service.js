(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_accreditation_ieps', Freecom_accreditation_ieps);

    Freecom_accreditation_ieps.$inject = ['$resource'];

    function Freecom_accreditation_ieps ($resource) {
        var resourceUrl =  'api/freecom-accreditation-ieps/:id';

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
