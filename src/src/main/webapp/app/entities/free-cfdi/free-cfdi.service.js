(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_cfdi', Free_cfdi);

    Free_cfdi.$inject = ['$resource', 'DateUtils'];

    function Free_cfdi ($resource, DateUtils) {
        var resourceUrl =  'api/free-cfdis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {idFree_cfdi: null,folio_fiscal: null,rfc_receiver: null,
                    fromDate: null, toDate: null, idState: null, serie: null, folio: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_expedition = DateUtils.convertDateTimeFromServer(data.date_expedition);
                        data.date_folio_fiscal_orig = DateUtils.convertDateTimeFromServer(data.date_folio_fiscal_orig);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
