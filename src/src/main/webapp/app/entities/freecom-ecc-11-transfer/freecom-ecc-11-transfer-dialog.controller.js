(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_transferDialogController', Freecom_ecc11_transferDialogController);

    Freecom_ecc11_transferDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_ecc11_transferDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.freecom_ecc11_transfer = entity;
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                type_tax: vm.freecom_ecc11_transfer.type_tax,
                rate: vm.freecom_ecc11_transfer.rate,
                amount: vm.freecom_ecc11_transfer.amount,
                id: null
            });
            vm.isSaving = false;
        }
    }
})();
