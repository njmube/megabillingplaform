(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_retentionsDialogController', Tax_retentionsDialogController);

    Tax_retentionsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_retentions', 'Tax_types'];

    function Tax_retentionsDialogController ($scope, $stateParams, $uibModalInstance, entity, Tax_retentions, Tax_types) {
        var vm = this;
        vm.tax_retentions = entity;
        vm.tax_typess = Tax_types.query();
        vm.load = function(id) {
            Tax_retentions.get({id : id}, function(result) {
                vm.tax_retentions = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_retentionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_retentions.id !== null) {
                Tax_retentions.update(vm.tax_retentions, onSaveSuccess, onSaveError);
            } else {
                Tax_retentions.save(vm.tax_retentions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
