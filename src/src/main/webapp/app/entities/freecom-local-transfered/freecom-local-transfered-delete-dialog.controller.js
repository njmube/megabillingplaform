(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_transferedDeleteController',Freecom_local_transferedDeleteController);

    Freecom_local_transferedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_local_transfered'];

    function Freecom_local_transferedDeleteController($uibModalInstance, entity, Freecom_local_transfered) {
        var vm = this;

        vm.freecom_local_transfered = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_local_transfered.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
