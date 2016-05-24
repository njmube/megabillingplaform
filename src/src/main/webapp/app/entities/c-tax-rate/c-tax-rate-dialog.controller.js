(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tax_rateDialogController', C_tax_rateDialogController);

    C_tax_rateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_tax_rate', 'Tax_concept'];

    function C_tax_rateDialogController ($scope, $stateParams, $uibModalInstance, entity, C_tax_rate, Tax_concept) {
        var vm = this;
        vm.c_tax_rate = entity;
        vm.tax_concepts = Tax_concept.query();
        vm.load = function(id) {
            C_tax_rate.get({id : id}, function(result) {
                vm.c_tax_rate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_tax_rateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_tax_rate.id !== null) {
                C_tax_rate.update(vm.c_tax_rate, onSaveSuccess, onSaveError);
            } else {
                C_tax_rate.save(vm.c_tax_rate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
