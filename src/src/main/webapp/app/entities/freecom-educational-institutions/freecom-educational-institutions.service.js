(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_educational_institutions', Freecom_educational_institutions);

    Freecom_educational_institutions.$inject = ['$resource'];

    function Freecom_educational_institutions ($resource) {
        var resourceUrl =  'api/freecom-educational-institutions/:id';

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
