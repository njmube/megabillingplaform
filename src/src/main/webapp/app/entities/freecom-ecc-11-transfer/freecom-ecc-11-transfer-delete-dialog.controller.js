(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_transferDeleteController',Freecom_ecc11_transferDeleteController);

    Freecom_ecc11_transferDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_ecc11_transfer'];

    function Freecom_ecc11_transferDeleteController($uibModalInstance, entity, Freecom_ecc11_transfer) {
        var vm = this;

        vm.freecom_ecc11_transfer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_ecc11_transfer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
