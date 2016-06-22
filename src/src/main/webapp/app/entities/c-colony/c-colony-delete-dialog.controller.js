(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_colonyDeleteController',C_colonyDeleteController);

    C_colonyDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_colony'];

    function C_colonyDeleteController($uibModalInstance, entity, C_colony) {
        var vm = this;

        vm.c_colony = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_colony.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
