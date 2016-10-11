(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Cfdi', Cfdi);

    Cfdi.$inject = ['$resource', 'DateUtils'];

    function Cfdi ($resource, DateUtils) {
        var resourceUrl =  'api/cfdis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true,
                params: {folio_fiscal: null,rfc_receiver: null,
                    fromDate: null,
                    toDate: null,
                    idcfdi_type_doc: null,
                    serie: null,
                    folio: null,
                    idaccount: null,
                    pre: null,
                    send: null,
                    cancel: null,
                    reciever: null}},
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
