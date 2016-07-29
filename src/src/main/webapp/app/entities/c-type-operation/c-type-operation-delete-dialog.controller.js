(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operationDeleteController',C_type_operationDeleteController);

    C_type_operationDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_type_operation'];

    function C_type_operationDeleteController($uibModalInstance, entity, C_type_operation) {
        var vm = this;
        vm.c_type_operation = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_type_operation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
