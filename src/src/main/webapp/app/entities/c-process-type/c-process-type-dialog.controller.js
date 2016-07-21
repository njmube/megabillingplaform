(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_process_typeDialogController', C_process_typeDialogController);

    C_process_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_process_type'];

    function C_process_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_process_type) {
        var vm = this;
        vm.c_process_type = entity;
        vm.load = function(id) {
            C_process_type.get({id : id}, function(result) {
                vm.c_process_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_process_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_process_type.id !== null) {
                C_process_type.update(vm.c_process_type, onSaveSuccess, onSaveError);
            } else {
                C_process_type.save(vm.c_process_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
