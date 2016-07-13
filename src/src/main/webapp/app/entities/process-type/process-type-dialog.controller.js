(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Process_typeDialogController', Process_typeDialogController);

    Process_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Process_type'];

    function Process_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Process_type) {
        var vm = this;
        vm.process_type = entity;
        vm.load = function(id) {
            Process_type.get({id : id}, function(result) {
                vm.process_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:process_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.process_type.id !== null) {
                Process_type.update(vm.process_type, onSaveSuccess, onSaveError);
            } else {
                Process_type.save(vm.process_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
