(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_transferedDialogController', Free_tax_transferedDialogController);

    Free_tax_transferedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_tax_transfered', 'Free_concept', 'Tax_types'];

    function Free_tax_transferedDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_tax_transfered, Free_concept, Tax_types) {
        var vm = this;
        vm.free_tax_transfered = entity;
        vm.free_concepts = Free_concept.query();
        vm.tax_typess = Tax_types.query();
        vm.load = function(id) {
            Free_tax_transfered.get({id : id}, function(result) {
                vm.free_tax_transfered = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_tax_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_tax_transfered.id !== null) {
                Free_tax_transfered.update(vm.free_tax_transfered, onSaveSuccess, onSaveError);
            } else {
                Free_tax_transfered.save(vm.free_tax_transfered, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
