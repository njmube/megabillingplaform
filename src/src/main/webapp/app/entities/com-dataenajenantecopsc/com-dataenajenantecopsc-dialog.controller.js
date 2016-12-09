(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataenajenantecopscDialogController', Com_dataenajenantecopscDialogController);

    Com_dataenajenantecopscDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_dataenajenantecopscDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_dataenajenantecopsc = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_dataenajenantecopsc);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
