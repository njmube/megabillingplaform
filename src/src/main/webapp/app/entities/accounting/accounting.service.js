(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Accounting', Accounting);

    Accounting.$inject = ['$resource'];

    function Accounting ($resource) {
        var resourceUrl =  'api/accountings/:id';

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
