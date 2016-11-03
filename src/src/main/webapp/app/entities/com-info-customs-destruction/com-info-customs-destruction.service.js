(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_info_customs_destruction', Com_info_customs_destruction);

    Com_info_customs_destruction.$inject = ['$resource', 'DateUtils'];

    function Com_info_customs_destruction ($resource, DateUtils) {
        var resourceUrl =  'api/com-info-customs-destructions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_expedition = DateUtils.convertLocalDateFromServer(data.date_expedition);
                    }
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
