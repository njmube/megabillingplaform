(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_info_customs_destruction', Freecom_info_customs_destruction);

    Freecom_info_customs_destruction.$inject = ['$resource', 'DateUtils'];

    function Freecom_info_customs_destruction ($resource, DateUtils) {
        var resourceUrl =  'api/freecom-info-customs-destructions/:id';

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
