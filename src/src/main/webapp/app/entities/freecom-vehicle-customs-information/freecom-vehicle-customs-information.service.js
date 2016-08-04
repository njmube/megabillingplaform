(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_vehicle_customs_information', Freecom_vehicle_customs_information);

    Freecom_vehicle_customs_information.$inject = ['$resource', 'DateUtils'];

    function Freecom_vehicle_customs_information ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-vehicle-customs-informations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_expedition = DateUtils.convertLocalDateFromServer(data.date_expedition);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_expedition = DateUtils.convertLocalDateToServer(data.date_expedition);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_expedition = DateUtils.convertLocalDateToServer(data.date_expedition);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
