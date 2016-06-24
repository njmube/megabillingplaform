(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_transferedDialogController', Tax_transferedDialogController);

    Tax_transferedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_transfered', 'Tax_types', 'Rate_type'];

    function Tax_transferedDialogController ($scope, $stateParams, $uibModalInstance, entity, Tax_transfered, Tax_types, Rate_type) {
        var vm = this;
        vm.tax_transfered = entity;
        vm.tax_typess = Tax_types.query();
        vm.rate_types = Rate_type.query();
        vm.load = function(id) {
            Tax_transfered.get({id : id}, function(result) {
                vm.tax_transfered = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_transfered.id !== null) {
                Tax_transfered.update(vm.tax_transfered, onSaveSuccess, onSaveError);
            } else {
                Tax_transfered.save(vm.tax_transfered, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
