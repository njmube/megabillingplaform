(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_stateDialogController', File_stateDialogController);

    File_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'File_state'];

    function File_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, File_state) {
        var vm = this;
        vm.file_state = entity;
        vm.load = function(id) {
            File_state.get({id : id}, function(result) {
                vm.file_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:file_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.file_state.id !== null) {
                File_state.update(vm.file_state, onSaveSuccess, onSaveError);
            } else {
                File_state.save(vm.file_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
