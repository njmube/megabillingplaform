(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tax_typeDialogController', Freecom_tax_typeDialogController);

    Freecom_tax_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_tax_type'];

    function Freecom_tax_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_tax_type) {
        var vm = this;
        vm.freecom_tax_type = entity;
        vm.load = function(id) {
            Freecom_tax_type.get({id : id}, function(result) {
                vm.freecom_tax_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_tax_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_tax_type.id !== null) {
                Freecom_tax_type.update(vm.freecom_tax_type, onSaveSuccess, onSaveError);
            } else {
                Freecom_tax_type.save(vm.freecom_tax_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
