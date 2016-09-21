(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_specific_descriptionsDialogController', Freecom_specific_descriptionsDialogController);

    Freecom_specific_descriptionsDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_specific_descriptionsDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.freecom_specific_descriptions = entity;
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_specific_descriptions);
            vm.isSaving = false;
        }
    }
})();
