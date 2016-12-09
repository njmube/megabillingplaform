(function() {
    'use strict';
    angular
        .module('megabillingplatformApp')
        .factory('Com_data_operation', Com_data_operation);

    Com_data_operation.$inject = ['$resource', 'DateUtils'];

    function Com_data_operation ($resource, DateUtils) {
        var resourceUrl =  'api/com-data-operations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateinstnotarial = DateUtils.convertLocalDateFromServer(data.dateinstnotarial);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateinstnotarial = DateUtils.convertLocalDateToServer(data.dateinstnotarial);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateinstnotarial = DateUtils.convertLocalDateToServer(data.dateinstnotarial);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
