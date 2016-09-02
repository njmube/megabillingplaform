(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_certificate', Taxpayer_certificate);

    Taxpayer_certificate.$inject = ['$resource', 'DateUtils'];

    function Taxpayer_certificate ($resource, DateUtils) {
        var resourceUrl =  'api/taxpayer-certificates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_certificate = DateUtils.convertLocalDateFromServer(data.date_certificate);
                        data.date_created_cert = DateUtils.convertLocalDateFromServer(data.date_created_cert);
                        data.date_expiration_cert = DateUtils.convertLocalDateFromServer(data.date_expiration_cert);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_certificate = DateUtils.convertLocalDateToServer(data.date_certificate);
                    data.date_created_cert = DateUtils.convertLocalDateToServer(data.date_created_cert);
                    data.date_expiration_cert = DateUtils.convertLocalDateToServer(data.date_expiration_cert);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_certificate = DateUtils.convertLocalDateToServer(data.date_certificate);
                    data.date_created_cert = DateUtils.convertLocalDateToServer(data.date_created_cert);
                    data.date_expiration_cert = DateUtils.convertLocalDateToServer(data.date_expiration_cert);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
