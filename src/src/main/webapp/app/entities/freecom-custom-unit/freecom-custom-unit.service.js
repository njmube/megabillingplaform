(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_custom_unit', Freecom_custom_unit);

    Freecom_custom_unit.$inject = ['$resource'];

    function Freecom_custom_unit ($resource) {
        var resourceUrl =  'api/freecom-custom-units/:id';

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
