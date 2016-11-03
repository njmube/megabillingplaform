(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_concept_fuel', Com_concept_fuel);

    Com_concept_fuel.$inject = ['$resource', 'DateUtils'];

    function Com_concept_fuel ($resource, DateUtils) {
        var resourceUrl =  'api/com-concept-fuels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
