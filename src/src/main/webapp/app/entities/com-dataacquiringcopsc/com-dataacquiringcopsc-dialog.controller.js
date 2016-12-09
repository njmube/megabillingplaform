(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataacquiringcopscDialogController', Com_dataacquiringcopscDialogController);

    Com_dataacquiringcopscDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_dataacquiringcopscDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_dataacquiringcopsc = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_dataacquiringcopsc);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
