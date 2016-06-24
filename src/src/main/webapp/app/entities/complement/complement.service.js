(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Complement', Complement);

    Complement.$inject = ['$resource'];

    function Complement ($resource) {
        var resourceUrl =  'api/complements/:id';

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
