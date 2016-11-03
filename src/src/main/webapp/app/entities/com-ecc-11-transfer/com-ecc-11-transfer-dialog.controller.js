(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_transferDialogController', Com_ecc_11_transferDialogController);

    Com_ecc_11_transferDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_ecc_11_transferDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.com_ecc_11_transfer = entity;
        vm.type_taxes = ["IVA","IEPS"];
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_ecc_11_transfer);
            vm.isSaving = false;
        }
    }
})();
