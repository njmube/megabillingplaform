(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_custom_unitDialogController', Com_custom_unitDialogController);

    Com_custom_unitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_custom_unit'];

    function Com_custom_unitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_custom_unit) {
        var vm = this;
        vm.com_custom_unit = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_custom_unitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_custom_unit.id !== null) {
                Com_custom_unit.update(vm.com_custom_unit, onSaveSuccess, onSaveError);
            } else {
                Com_custom_unit.save(vm.com_custom_unit, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
