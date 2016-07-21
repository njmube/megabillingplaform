(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_scope_typeDetailController', C_scope_typeDetailController);

    C_scope_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_scope_type'];

    function C_scope_typeDetailController($scope, $rootScope, $stateParams, entity, C_scope_type) {
        var vm = this;
        vm.c_scope_type = entity;
        vm.load = function (id) {
            C_scope_type.get({id: id}, function(result) {
                vm.c_scope_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_scope_typeUpdate', function(event, result) {
            vm.c_scope_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
