(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Branch_office', Branch_office);

    Branch_office.$inject = ['$resource'];

    function Branch_office ($resource) {
        var resourceUrl =  'api/branch-offices/:id';

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
