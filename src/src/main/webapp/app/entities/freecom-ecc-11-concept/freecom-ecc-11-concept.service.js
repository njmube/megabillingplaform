(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_ecc11_concept', Freecom_ecc11_concept);

    Freecom_ecc11_concept.$inject = ['$resource', 'DateUtils'];

    function Freecom_ecc11_concept ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-ecc-11-concepts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
