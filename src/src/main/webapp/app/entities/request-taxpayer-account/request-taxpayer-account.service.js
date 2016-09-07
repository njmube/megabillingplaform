(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Request_taxpayer_account', Request_taxpayer_account);

    Request_taxpayer_account.$inject = ['$resource', 'DateUtils'];

    function Request_taxpayer_account ($resource, DateUtils) {
        var resourceUrl =  'api/request-taxpayer-accounts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, params: {datefrom:null,dateto: null, request_state: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_request = DateUtils.convertDateTimeFromServer(data.date_request);
                        data.date_born = DateUtils.convertLocalDateFromServer(data.date_born);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
