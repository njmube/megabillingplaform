(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_determinedDialogController', Freecom_determinedDialogController);

    Freecom_determinedDialogController.$inject = ['$uibModalInstance', 'entity', 'Freecom_tax_type'];

    function Freecom_determinedDialogController ($uibModalInstance, entity, Freecom_tax_type) {
        var vm = this;
        vm.freecom_determined = entity;
        vm.freecom_tax_types = Freecom_tax_type.query();
        vm.type_taxes = ["IVA","IEPS"];

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close(vm.freecom_determined);

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
