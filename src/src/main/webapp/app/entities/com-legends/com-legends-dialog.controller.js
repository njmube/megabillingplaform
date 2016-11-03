(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_legendsDialogController', Com_legendsDialogController);

    Com_legendsDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_legendsDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_legends = entity;

        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_legends);
            vm.isSaving = false;
        }
    }
})();
