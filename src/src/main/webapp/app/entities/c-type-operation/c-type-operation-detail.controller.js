(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operationDetailController', C_type_operationDetailController);

    C_type_operationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_type_operation'];

    function C_type_operationDetailController($scope, $rootScope, $stateParams, entity, C_type_operation) {
        var vm = this;
        vm.c_type_operation = entity;
        vm.load = function (id) {
            C_type_operation.get({id: id}, function(result) {
                vm.c_type_operation = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_type_operationUpdate', function(event, result) {
            vm.c_type_operation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
