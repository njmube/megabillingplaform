(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_transferDialogController', Freecom_ecc11_transferDialogController);

    Freecom_ecc11_transferDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_ecc11_transferDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.freecom_ecc11_transfer = entity;
        vm.type_taxes = ["IVA","IEPS"];
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_ecc11_transfer);
            vm.isSaving = false;
        }
    }
})();
