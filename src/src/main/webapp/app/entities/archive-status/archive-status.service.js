(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Archive_status', Archive_status);

    Archive_status.$inject = ['$resource', 'DateUtils'];

    function Archive_status ($resource, DateUtils) {
        var resourceUrl =  'api/archive-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertLocalDateFromServer(data.date);
                    data.date1 = DateUtils.convertDateTimeFromServer(data.date1);
                    data.date_born = DateUtils.convertLocalDateFromServer(data.date_born);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
