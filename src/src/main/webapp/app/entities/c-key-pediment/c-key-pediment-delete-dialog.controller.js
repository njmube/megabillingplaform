(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_key_pedimentDeleteController',C_key_pedimentDeleteController);

    C_key_pedimentDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_key_pediment'];

    function C_key_pedimentDeleteController($uibModalInstance, entity, C_key_pediment) {
        var vm = this;

        vm.c_key_pediment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_key_pediment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
