(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Free_digital_certificate', Free_digital_certificate);

    Free_digital_certificate.$inject = ['$resource'];

    function Free_digital_certificate ($resource) {
        var resourceUrl =  'api/free-digital-certificates/:id';

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
