(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_estateDialogController', C_type_estateDialogController);

    C_type_estateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_type_estate'];

    function C_type_estateDialogController ($scope, $stateParams, $uibModalInstance, entity, C_type_estate) {
        var vm = this;
        vm.c_type_estate = entity;
        vm.load = function(id) {
            C_type_estate.get({id : id}, function(result) {
                vm.c_type_estate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_type_estateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_type_estate.id !== null) {
                C_type_estate.update(vm.c_type_estate, onSaveSuccess, onSaveError);
            } else {
                C_type_estate.save(vm.c_type_estate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
