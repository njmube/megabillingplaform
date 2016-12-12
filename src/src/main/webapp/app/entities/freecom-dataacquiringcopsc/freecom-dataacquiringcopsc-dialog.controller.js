(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataacquiringcopscDialogController', Freecom_dataacquiringcopscDialogController);

    Freecom_dataacquiringcopscDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_dataacquiringcopscDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.freecom_dataacquiringcopsc = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_dataacquiringcopsc);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
