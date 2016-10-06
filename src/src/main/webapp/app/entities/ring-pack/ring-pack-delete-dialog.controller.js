(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Ring_packDeleteController',Ring_packDeleteController);

    Ring_packDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ring_pack'];

    function Ring_packDeleteController($uibModalInstance, entity, Ring_pack) {
        var vm = this;

        vm.ring_pack = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ring_pack.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
