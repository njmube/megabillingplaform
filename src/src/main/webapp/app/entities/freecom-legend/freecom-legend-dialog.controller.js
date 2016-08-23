(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_legendDialogController', Freecom_legendDialogController);

    Freecom_legendDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_legendDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.freecom_legend = entity;
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_legend);
            vm.isSaving = false;
        }

    }
})();
