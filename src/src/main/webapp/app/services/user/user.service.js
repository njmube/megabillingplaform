(function () {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true,
                params: {filterrfc: null,datefrom: null,dateto: null,
                    stateuser: null, role: null, filterlogin: null}},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST'
            },
            'update': { method:'PUT'
            },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();
