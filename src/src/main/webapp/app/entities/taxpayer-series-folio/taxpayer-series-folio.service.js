(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_series_folio', Taxpayer_series_folio);

    Taxpayer_series_folio.$inject = ['$resource', 'DateUtils'];

    function Taxpayer_series_folio ($resource, DateUtils) {
        var resourceUrl =  'api/taxpayer-series-folios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {taxpayeraccount: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_creation = DateUtils.convertLocalDateFromServer(data.date_creation);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_creation = DateUtils.convertLocalDateToServer(data.date_creation);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_creation = DateUtils.convertLocalDateToServer(data.date_creation);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
