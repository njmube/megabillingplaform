(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_retentionsDialogController', Free_tax_retentionsDialogController);

    Free_tax_retentionsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_tax_retentions', 'Free_concept', 'Tax_types'];

    function Free_tax_retentionsDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_tax_retentions, Free_concept, Tax_types) {
        var vm = this;
        vm.free_tax_retentions = entity;
        vm.free_concepts = Free_concept.query();
        vm.tax_typess = Tax_types.query();
        vm.load = function(id) {
            Free_tax_retentions.get({id : id}, function(result) {
                vm.free_tax_retentions = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_tax_retentionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_tax_retentions.id !== null) {
                Free_tax_retentions.update(vm.free_tax_retentions, onSaveSuccess, onSaveError);
            } else {
                Free_tax_retentions.save(vm.free_tax_retentions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
