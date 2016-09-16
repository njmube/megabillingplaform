(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Taxpayer_mail_accounts', Taxpayer_mail_accounts);

    Taxpayer_mail_accounts.$inject = ['$resource'];

    function Taxpayer_mail_accounts ($resource) {
        var resourceUrl =  'api/taxpayer-mail-accounts/:id';

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
