(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_spei_third', Com_spei_third);

    Com_spei_third.$inject = ['$resource', 'DateUtils'];

    function Com_spei_third ($resource, DateUtils) {
        var resourceUrl =  'api/com-spei-thirds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_operation = DateUtils.convertLocalDateFromServer(data.date_operation);
                        data.hour = DateUtils.convertDateTimeFromServer(data.hour);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_operation = DateUtils.convertLocalDateToServer(data.date_operation);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_operation = DateUtils.convertLocalDateToServer(data.date_operation);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
