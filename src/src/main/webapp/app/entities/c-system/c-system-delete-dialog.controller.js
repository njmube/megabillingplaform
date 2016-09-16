(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_systemDeleteController',C_systemDeleteController);

    C_systemDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_system'];

    function C_systemDeleteController($uibModalInstance, entity, C_system) {
        var vm = this;

        vm.c_system = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_system.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
