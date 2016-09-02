(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_retentions_transferedDialogController', Freecom_retentions_transferedDialogController);

    Freecom_retentions_transferedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_retentions_transfered'];

    function Freecom_retentions_transferedDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_retentions_transfered) {
        var vm = this;
        vm.freecom_retentions_transfered = entity;
        vm.load = function(id) {
            Freecom_retentions_transfered.get({id : id}, function(result) {
                vm.freecom_retentions_transfered = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
            if(vm.freecom_retentions_transfered.implocretentions == null){
                vm.freecom_retentions_transfered.implocretentions = "-";
            }
            else if(vm.freecom_retentions_transfered.imploctransfered == null){
                vm.freecom_retentions_transfered.imploctransfered = "-";
            }
            $uibModalInstance.close(vm.freecom_retentions_transfered);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
