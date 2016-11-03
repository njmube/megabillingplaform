(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_specific_descriptionsDialogController', Com_specific_descriptionsDialogController);

    Com_specific_descriptionsDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_specific_descriptionsDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.com_specific_descriptions = entity;
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_specific_descriptions);
            vm.isSaving = false;
        }
    }
})();
