(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_desc_estate', Freecom_desc_estate);

    Freecom_desc_estate.$inject = ['$resource'];

    function Freecom_desc_estate ($resource) {
        var resourceUrl =  'api/freecom-desc-estates/:id';

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
