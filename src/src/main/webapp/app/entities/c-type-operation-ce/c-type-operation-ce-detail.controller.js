(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operation_ceDetailController', C_type_operation_ceDetailController);

    C_type_operation_ceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_type_operation_ce'];

    function C_type_operation_ceDetailController($scope, $rootScope, $stateParams, entity, C_type_operation_ce) {
        var vm = this;

        vm.c_type_operation_ce = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_type_operation_ceUpdate', function(event, result) {
            vm.c_type_operation_ce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
