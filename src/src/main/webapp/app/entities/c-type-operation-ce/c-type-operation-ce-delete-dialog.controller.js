(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_operation_ceDeleteController',C_type_operation_ceDeleteController);

    C_type_operation_ceDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_type_operation_ce'];

    function C_type_operation_ceDeleteController($uibModalInstance, entity, C_type_operation_ce) {
        var vm = this;

        vm.c_type_operation_ce = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_type_operation_ce.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
