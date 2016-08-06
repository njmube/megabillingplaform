(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_concept_fuel', Freecom_concept_fuel);

    Freecom_concept_fuel.$inject = ['$resource', 'DateUtils'];

    function Freecom_concept_fuel ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-concept-fuels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
