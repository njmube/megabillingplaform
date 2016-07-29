(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_retentions_transferedDialogController', Freecom_retentions_transferedDialogController);

    Freecom_retentions_transferedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_retentions_transfered', 'Freecom_local_taxes'];

    function Freecom_retentions_transferedDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_retentions_transfered, Freecom_local_taxes) {
        var vm = this;
        vm.freecom_retentions_transfered = entity;
        vm.freecom_local_taxess = Freecom_local_taxes.query();
        vm.load = function(id) {
            Freecom_retentions_transfered.get({id : id}, function(result) {
                vm.freecom_retentions_transfered = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_retentions_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_retentions_transfered.id !== null) {
                Freecom_retentions_transfered.update(vm.freecom_retentions_transfered, onSaveSuccess, onSaveError);
            } else {
                Freecom_retentions_transfered.save(vm.freecom_retentions_transfered, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
