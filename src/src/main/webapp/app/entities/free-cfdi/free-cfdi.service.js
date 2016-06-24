(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_cfdi', Free_cfdi);

    Free_cfdi.$inject = ['$resource', 'DateUtils'];

    function Free_cfdi ($resource, DateUtils) {
        var resourceUrl =  'api/free-cfdis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                    data.date_folio_fiscal_orig = DateUtils.convertDateTimeFromServer(data.date_folio_fiscal_orig);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
