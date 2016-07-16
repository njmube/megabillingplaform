(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('C_money', C_money);

    C_money.$inject = ['$resource'];

    function C_money ($resource) {
        var resourceUrl =  'api/c-monies/:id';

        return $resource(resourceUrl, {}, {
            'query': {
				method: 'GET',
				isArray: true
			},
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
