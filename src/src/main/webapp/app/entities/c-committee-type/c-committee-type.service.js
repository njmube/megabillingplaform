(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_committee_type', C_committee_type);

    C_committee_type.$inject = ['$resource'];

    function C_committee_type ($resource) {
        var resourceUrl =  'api/c-committee-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
