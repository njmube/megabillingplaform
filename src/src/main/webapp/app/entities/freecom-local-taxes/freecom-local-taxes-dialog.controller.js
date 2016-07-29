(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_taxesDialogController', Freecom_local_taxesDialogController);

    Freecom_local_taxesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_local_taxes', 'Free_cfdi'];

    function Freecom_local_taxesDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_local_taxes, Free_cfdi) {
        var vm = this;
        vm.freecom_local_taxes = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_local_taxes.get({id : id}, function(result) {
                vm.freecom_local_taxes = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_local_taxesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_local_taxes.id !== null) {
                Freecom_local_taxes.update(vm.freecom_local_taxes, onSaveSuccess, onSaveError);
            } else {
                Freecom_local_taxes.save(vm.freecom_local_taxes, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
