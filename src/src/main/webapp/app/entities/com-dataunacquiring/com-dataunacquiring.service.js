(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_dataunacquiring', Com_dataunacquiring);

    Com_dataunacquiring.$inject = ['$resource'];

    function Com_dataunacquiring ($resource) {
        var resourceUrl =  'api/com-dataunacquirings/:id';

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
