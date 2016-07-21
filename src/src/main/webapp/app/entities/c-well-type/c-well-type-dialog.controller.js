(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_well_typeDialogController', C_well_typeDialogController);

    C_well_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_well_type'];

    function C_well_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_well_type) {
        var vm = this;
        vm.c_well_type = entity;
        vm.load = function(id) {
            C_well_type.get({id : id}, function(result) {
                vm.c_well_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_well_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_well_type.id !== null) {
                C_well_type.update(vm.c_well_type, onSaveSuccess, onSaveError);
            } else {
                C_well_type.save(vm.c_well_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
