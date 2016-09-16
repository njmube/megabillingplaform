(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_ftp_account', Taxpayer_ftp_account);

    Taxpayer_ftp_account.$inject = ['$resource'];

    function Taxpayer_ftp_account ($resource) {
        var resourceUrl =  'api/taxpayer-ftp-accounts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
