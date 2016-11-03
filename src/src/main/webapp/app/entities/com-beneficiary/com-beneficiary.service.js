(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_beneficiary', Com_beneficiary);

    Com_beneficiary.$inject = ['$resource'];

    function Com_beneficiary ($resource) {
        var resourceUrl =  'api/com-beneficiaries/:id';

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
