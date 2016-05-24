(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDialogController', Tax_conceptDialogController);

    Tax_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_concept', 'C_tax_rate'];

    function Tax_conceptDialogController ($scope, $stateParams, $uibModalInstance, entity, Tax_concept, C_tax_rate) {
        var vm = this;
        vm.tax_concept = entity;
        vm.c_tax_rates = C_tax_rate.query();
        vm.load = function(id) {
            Tax_concept.get({id : id}, function(result) {
                vm.tax_concept = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_concept.id !== null) {
                Tax_concept.update(vm.tax_concept, onSaveSuccess, onSaveError);
            } else {
                Tax_concept.save(vm.tax_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
