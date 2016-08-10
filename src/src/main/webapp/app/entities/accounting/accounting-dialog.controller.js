(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDialogController', AccountingDialogController);

    AccountingDialogController.$inject = ['$uibModalInstance', 'entity'];

    function AccountingDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.accounting = entity;

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                keyaccounting: vm.accounting.keyaccounting,
                id: null
            });

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
