(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Digital_certificate', Digital_certificate);

    Digital_certificate.$inject = ['$resource'];

    function Digital_certificate ($resource) {
        var resourceUrl =  'api/digital-certificates/:id';

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
