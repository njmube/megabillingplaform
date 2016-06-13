(function () {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .factory('User', User);

    User.$inject = ['$resource', 'DateUtils'];

    function User ($resource, DateUtils) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_born = DateUtils.convertLocalDateFromServer(data.date_born);
                    return data;
                }
            },
            'save': { method:'POST' ,
                transformRequest: function (data) {
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);
                }
            },
            'update': { method:'PUT',
                transformRequest: function (data) {
                    data.date_born = DateUtils.convertLocalDateToServer(data.date_born);
                    return angular.toJson(data);}
            },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();
