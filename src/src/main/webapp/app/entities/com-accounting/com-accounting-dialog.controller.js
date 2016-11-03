(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accountingDialogController', Com_accountingDialogController);

    Com_accountingDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_accountingDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_accounting = entity;

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close({
                keyaccounting: vm.com_accounting.keyaccounting,
                id: null
            });

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
