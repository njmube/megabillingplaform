(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_foreign_tourist_passenger', Freecom_foreign_tourist_passenger);

    Freecom_foreign_tourist_passenger.$inject = ['$resource', 'DateUtils'];

    function Freecom_foreign_tourist_passenger ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-foreign-tourist-passengers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_traffic = DateUtils.convertDateTimeFromServer(data.date_traffic);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
