(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataenajenantecopscDialogController', Freecom_dataenajenantecopscDialogController);

    Freecom_dataenajenantecopscDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_dataenajenantecopscDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.freecom_dataenajenantecopsc = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_dataenajenantecopsc);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
