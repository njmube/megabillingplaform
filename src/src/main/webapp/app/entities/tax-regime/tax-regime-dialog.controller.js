(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_regimeDialogController', Tax_regimeDialogController);

    Tax_regimeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_regime'];

    function Tax_regimeDialogController ($scope, $stateParams, $uibModalInstance, entity, Tax_regime) {
        var vm = this;
        vm.tax_regime = entity;
        vm.load = function(id) {
            Tax_regime.get({id : id}, function(result) {
                vm.tax_regime = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_regimeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_regime.id !== null) {
                Tax_regime.update(vm.tax_regime, onSaveSuccess, onSaveError);
            } else {
                Tax_regime.save(vm.tax_regime, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
