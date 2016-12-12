(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Freecom_dataunacquiring', Freecom_dataunacquiring);

    Freecom_dataunacquiring.$inject = ['$resource'];

    function Freecom_dataunacquiring ($resource) {
        var resourceUrl =  'api/freecom-dataunacquirings/:id';

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
