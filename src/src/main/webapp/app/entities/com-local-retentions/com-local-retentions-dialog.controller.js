(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_retentionsDialogController', Com_local_retentionsDialogController);

    Com_local_retentionsDialogController.$inject = ['$uibModalInstance', 'entity', 'transfered_entity'];

    function Com_local_retentionsDialogController ($uibModalInstance, entity, transfered_entity) {
        var vm = this;

        vm.com_local_retentions = entity;
        vm.use_local_retentions = false;

        vm.com_local_transfered = transfered_entity;
        vm.use_local_transfered = false;

        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            var result;
            if(vm.use_local_retentions && vm.use_local_transfered){
                result = {
                    retentions: vm.com_local_retentions,
                    transfered: vm.com_local_transfered
                };
            }
            else if(vm.use_local_retentions){
                result = {
                    retentions: vm.com_local_retentions,
                    transfered: null
                };
            }
            else if(vm.use_local_transfered){
                result = {
                    retentions: null,
                    transfered: vm.com_local_transfered
                };
            }

            $uibModalInstance.close(result);
            vm.isSaving = false;
        }
    }
})();
