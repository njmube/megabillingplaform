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

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                rate: vm.freecom_determined.rate,
                amount: vm.freecom_determined.amount,
                freecom_tax_type: vm.freecom_determined.freecom_tax_type,
                id: null
            });

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
